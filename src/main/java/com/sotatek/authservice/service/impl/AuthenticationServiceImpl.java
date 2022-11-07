package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.constant.RedisConstant;
import com.sotatek.authservice.model.entity.RefreshTokenEntity;
import com.sotatek.authservice.model.entity.RoleEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.authservice.model.enums.ERole;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.request.RefreshTokenRequest;
import com.sotatek.authservice.model.request.SignInRequest;
import com.sotatek.authservice.model.request.SignOutRequest;
import com.sotatek.authservice.model.request.SignUpRequest;
import com.sotatek.authservice.model.request.WalletRequest;
import com.sotatek.authservice.model.response.RefreshTokenResponse;
import com.sotatek.authservice.model.response.SignInResponse;
import com.sotatek.authservice.model.response.SignUpResponse;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.repository.RoleRepository;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.repository.WalletRepository;
import com.sotatek.authservice.service.AuthenticationService;
import com.sotatek.authservice.service.RefreshTokenService;
import com.sotatek.authservice.service.UserHistoryService;
import com.sotatek.authservice.service.WalletService;
import com.sotatek.authservice.util.NonceUtils;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.TokenRefreshException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import com.sotatek.cardanocommonapi.utils.StringUtils;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

  @Value("${nonce.expirationMs}")
  private Long nonceExpirationMs;

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private WalletRepository walletRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private RefreshTokenService refreshTokenService;

  @Autowired
  private UserHistoryService userHistoryService;

  @Autowired
  private WalletService walletService;

  @Autowired
  private PasswordEncoder encoder;

  private static final String TOKEN_TYPE = "Bearer";

  @Override
  public void blacklistJwt(String token, String username) {
    if (!isTokenBlacklisted(token)) {
      redisTemplate.opsForValue().set(RedisConstant.JWT + token, username);
    }
  }

  @Override
  public boolean isTokenBlacklisted(String token) {
    if (Boolean.TRUE.equals(StringUtils.isNullOrEmpty(token))) {
      throw new BusinessException(CommonErrorCode.INVALID_TOKEN);
    }
    return redisTemplate.opsForValue().get(RedisConstant.JWT + token) != null;
  }

  @Override
  public ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest) {
    String signature = signInRequest.getSignature();
    String ipAddress = signInRequest.getIpAddress();
    String stakeAddress = signInRequest.getStakeAddress();
    String nonceFromSign = NonceUtils.getNonceFromSignature(signature);
    UserEntity user = userRepository.findByStakeAddress(stakeAddress)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
    WalletEntity wallet = walletRepository.findByStakeAddress(stakeAddress)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
    if (wallet.getExpiryDateNonce().compareTo(Instant.now()) < 0) {
      walletService.updateNewNonce(wallet);
      throw new BusinessException(CommonErrorCode.NONCE_EXPIRED);
    }
    Authentication authentication = null;
    try {
      authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(stakeAddress, nonceFromSign));
    } catch (AuthenticationException e) {
      log.error("Exception authentication: " + e.getMessage());
      throw new BusinessException(CommonErrorCode.SIGNATURE_INVALID);
    }
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String accessToken = jwtProvider.generateJwtToken(authentication, user.getUsername());
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(),
        accessToken, stakeAddress);
    userHistoryService.saveUserHistory(EUserAction.LOGIN, ipAddress, Instant.now(), true,
        user.getUsername());
    walletService.updateNewNonce(wallet);
    return ResponseEntity.ok(
        SignInResponse.builder().token(accessToken).walletId(userDetails.getId())
            .username(user.getUsername()).email(userDetails.getEmail()).role(roles)
            .tokenType(TOKEN_TYPE).refreshToken(refreshToken.getToken()).build());
  }

  @Override
  public ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest) {
    String username = signUpRequest.getUsername();
    if (Boolean.TRUE.equals(userRepository.existsByUsername(username))) {
      return ResponseEntity.badRequest().body(new SignUpResponse("Username is already exist"));
    }
    WalletRequest walletRequest = signUpRequest.getWallet();
    if (Boolean.TRUE.equals(
        walletRepository.existsByStakeAddress(walletRequest.getStakeAddress()))) {
      return ResponseEntity.badRequest().body(new SignUpResponse("Wallet is already in exist"));
    }
    String nonce = NonceUtils.createNonce();
    UserEntity user = UserEntity.builder().username(username).email(signUpRequest.getEmail())
        .phone(signUpRequest.getPhone()).avatar(signUpRequest.getAvatar()).build();
    user.setRoles(addRoleForUser(signUpRequest.getRoles()));
    UserEntity userSave = userRepository.save(user);
    WalletEntity wallet = WalletEntity.builder().stakeAddress(walletRequest.getStakeAddress())
        .walletName(walletRequest.getWalletName()).balanceAtLogin(walletRequest.getBalanceAtLogin())
        .networkId(walletRequest.getNetworkId()).networkType(walletRequest.getNetworkType())
        .nonce(nonce).nonceEncode(encoder.encode(nonce))
        .expiryDateNonce(Instant.now().plusMillis(nonceExpirationMs)).user(userSave).build();
    walletRepository.save(wallet);
    userHistoryService.saveUserHistory(EUserAction.CREATED, signUpRequest.getIpAddress(),
        Instant.now(), true, user.getUsername());
    return ResponseEntity.ok(new SignUpResponse("Success", nonce));
  }

  @Override
  public ResponseEntity<RefreshTokenResponse> refreshToken(
      RefreshTokenRequest refreshTokenRequest) {
    String tokenOfRefreshToken = refreshTokenRequest.getRefreshToken();
    RefreshTokenEntity refreshToken = refreshTokenService.findByToken(tokenOfRefreshToken)
        .orElseThrow(() -> new TokenRefreshException(CommonErrorCode.REFRESH_TOKEN_IS_NOT_EXIST));
    final String accessToken = refreshToken.getAccessToken();
    final String username = refreshToken.getUser().getUsername();
    blacklistJwt(accessToken, username);
    refreshTokenService.verifyExpiration(refreshToken);
    WalletEntity wallet = walletRepository.findByStakeAddress(refreshToken.getStakeAddress())
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
    String token = jwtProvider.generateTokenFromRefreshToken(username, wallet.getId());
    refreshToken.setAccessToken(token);
    refreshTokenService.updateRefreshToken(refreshToken);
    return ResponseEntity.ok(
        RefreshTokenResponse.builder().accessToken(token).refreshToken(tokenOfRefreshToken)
            .tokenType(TOKEN_TYPE).build());
  }

  @Override
  public ResponseEntity<String> signOut(SignOutRequest signOutRequest) {
    String accessToken = signOutRequest.getAccessToken();
    String username = signOutRequest.getUsername();
    String ipAddress = signOutRequest.getIpAddress();
    if (Boolean.TRUE.equals(StringUtils.isNullOrEmpty(accessToken))) {
      throw new BusinessException(CommonErrorCode.INVALID_TOKEN);
    }
    refreshTokenService.revokeRefreshToken(signOutRequest.getRefreshToken());
    userHistoryService.saveUserHistory(EUserAction.LOGOUT, ipAddress, Instant.now(), true,
        username);
    blacklistJwt(accessToken, username);
    return ResponseEntity.ok("Success");
  }

  private Set<RoleEntity> addRoleForUser(Set<Integer> iRoles) {
    Set<RoleEntity> roles = new HashSet<>();
    if (iRoles == null || iRoles.isEmpty()) {
      RoleEntity rUser = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
      roles.add(rUser);
    } else {
      iRoles.forEach(role -> {
        switch (role) {
          case 1:
            RoleEntity rAdmin = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(
                () -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
            roles.add(rAdmin);
            break;
          case 2:
            RoleEntity rUser = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(
                () -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
            roles.add(rUser);
            break;
          default:
            //Todo
        }
      });
    }
    return roles;
  }
}
