package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.constant.CommonConstant;
import com.sotatek.authservice.mapper.UserMapper;
import com.sotatek.authservice.mapper.WalletMapper;
import com.sotatek.authservice.model.entity.RefreshTokenEntity;
import com.sotatek.authservice.model.entity.RoleEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.authservice.model.enums.ERole;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.request.auth.RefreshTokenRequest;
import com.sotatek.authservice.model.request.auth.SignInRequest;
import com.sotatek.authservice.model.request.auth.SignOutRequest;
import com.sotatek.authservice.model.request.auth.SignUpRequest;
import com.sotatek.authservice.model.request.auth.TransfersWalletRequest;
import com.sotatek.authservice.model.request.auth.WalletRequest;
import com.sotatek.authservice.model.response.RefreshTokenResponse;
import com.sotatek.authservice.model.response.SignInResponse;
import com.sotatek.authservice.model.response.SignUpResponse;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.provider.RedisProvider;
import com.sotatek.authservice.repository.RoleRepository;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.repository.WalletRepository;
import com.sotatek.authservice.service.AuthenticationService;
import com.sotatek.authservice.service.RefreshTokenService;
import com.sotatek.authservice.service.UserHistoryService;
import com.sotatek.authservice.service.WalletService;
import com.sotatek.authservice.util.NonceUtils;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.IgnoreRollbackException;
import com.sotatek.cardanocommonapi.exceptions.TokenRefreshException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

  @Value("${nonce.expirationMs}")
  private Long nonceExpirationMs;

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final WalletRepository walletRepository;

  private final AuthenticationManager authenticationManager;

  private final JwtProvider jwtProvider;

  private final RefreshTokenService refreshTokenService;

  private final UserHistoryService userHistoryService;

  private final WalletService walletService;

  private final PasswordEncoder encoder;

  private final RedisProvider redisProvider;

  private static final UserMapper userMapper = UserMapper.INSTANCE;

  private static final WalletMapper walletMapper = WalletMapper.INSTANCE;

  @Transactional(rollbackFor = {RuntimeException.class}, noRollbackFor = {
      IgnoreRollbackException.class})
  @Override
  public ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest) {
    log.info("login with cardano wallet is running...");
    String signature = signInRequest.getSignature();
    String stakeAddress = signInRequest.getStakeAddress();
    String nonceFromSign = NonceUtils.getNonceFromSignature(signature);
    UserEntity user = userRepository.findByStakeAddress(stakeAddress)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    WalletEntity wallet = walletRepository.findByStakeAddress(stakeAddress)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.WALLET_IS_NOT_EXIST));
    if (wallet.getExpiryDateNonce().compareTo(Instant.now()) < 0) {
      log.error("error: nonce value is expired");
      walletService.updateNewNonce(wallet);
      throw new IgnoreRollbackException(CommonErrorCode.NONCE_EXPIRED);
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
    RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user.getId(),
        stakeAddress);
    userHistoryService.saveUserHistory(EUserAction.LOGIN, signInRequest.getIpAddress(),
        Instant.now(), true, stakeAddress, user);
    walletService.updateNewNonce(wallet);
    return ResponseEntity.ok(
        SignInResponse.builder().token(accessToken).walletId(userDetails.getId())
            .username(user.getUsername()).email(userDetails.getEmail())
            .tokenType(CommonConstant.TOKEN_TYPE).refreshToken(refreshToken.getToken()).build());
  }

  @Transactional(rollbackFor = {RuntimeException.class})
  @Override
  public ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest) {
    String username = signUpRequest.getUsername();
    if (Boolean.TRUE.equals(userRepository.existsByUsername(username))) {
      throw new BusinessException(CommonErrorCode.USERNAME_IS_ALREADY_EXIST);
    }
    WalletRequest walletRequest = signUpRequest.getWallet();
    if (Boolean.TRUE.equals(
        walletRepository.existsByStakeAddress(walletRequest.getStakeAddress()))) {
      throw new BusinessException(CommonErrorCode.WALLET_IS_ALREADY_EXIST);
    }
    String nonce = NonceUtils.createNonce();
    UserEntity user = userMapper.requestToEntity(signUpRequest);
    user.setRoles(addRoleForUser(ERole.ROLE_USER));
    UserEntity userSave = userRepository.save(user);
    WalletEntity wallet = walletMapper.requestToEntity(walletRequest);
    wallet.setNonce(nonce);
    wallet.setNonceEncode(encoder.encode(nonce));
    wallet.setExpiryDateNonce(Instant.now().plusMillis(nonceExpirationMs));
    wallet.setUser(userSave);
    walletRepository.save(wallet);
    userHistoryService.saveUserHistory(EUserAction.CREATED, null, Instant.now(), true,
        walletRequest.getStakeAddress(), userSave);
    return ResponseEntity.ok(new SignUpResponse(CommonConstant.RESPONSE_SUCCESS, nonce));
  }

  @Transactional(rollbackFor = {RuntimeException.class})
  @Override
  public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest,
      HttpServletRequest httpServletRequest) {
    String tokenOfRefreshToken = refreshTokenRequest.getRefreshToken();
    RefreshTokenEntity refreshToken = refreshTokenService.findByToken(tokenOfRefreshToken)
        .orElseThrow(() -> new TokenRefreshException(CommonErrorCode.REFRESH_TOKEN_IS_NOT_EXIST));
    final String accessToken = jwtProvider.parseJwt(httpServletRequest);
    final String username = refreshToken.getUser().getUsername();
    redisProvider.blacklistJwt(accessToken, username);
    refreshTokenService.verifyExpiration(refreshToken);
    WalletEntity wallet = walletRepository.findByStakeAddress(refreshToken.getStakeAddress())
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
    String token = jwtProvider.generateJwtTokenFromUsername(username, wallet.getId());
    return ResponseEntity.ok(
        RefreshTokenResponse.builder().accessToken(token).refreshToken(tokenOfRefreshToken)
            .tokenType(CommonConstant.TOKEN_TYPE).build());
  }

  @Transactional(rollbackFor = {RuntimeException.class})
  @Override
  public ResponseEntity<String> signOut(SignOutRequest signOutRequest,
      HttpServletRequest httpServletRequest) {
    String username = signOutRequest.getUsername();
    String refreshToken = signOutRequest.getRefreshToken();
    String accessToken = jwtProvider.parseJwt(httpServletRequest);
    refreshTokenService.revokeRefreshToken(refreshToken);
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    userHistoryService.saveUserHistory(EUserAction.LOGOUT, null, Instant.now(), true, null, user);
    redisProvider.blacklistJwt(accessToken, username);
    return ResponseEntity.ok(CommonConstant.RESPONSE_SUCCESS);
  }

  @Transactional(rollbackFor = {RuntimeException.class})
  @Override
  public ResponseEntity<SignInResponse> transfersWallet(
      TransfersWalletRequest transfersWalletRequest, HttpServletRequest httpServletRequest) {
    String accessToken = jwtProvider.parseJwt(httpServletRequest);
    jwtProvider.validateJwtToken(accessToken);
    Long walletId = null;
    String username = transfersWalletRequest.getUsername();
    WalletRequest walletRequest = transfersWalletRequest.getWallet();
    String stakeAddress = walletRequest.getStakeAddress();
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    Optional<WalletEntity> walletOpt = walletRepository.findByStakeAddress(stakeAddress);
    if (walletOpt.isEmpty()) {
      String nonce = NonceUtils.createNonce();
      WalletEntity wallet = walletMapper.requestToEntity(walletRequest);
      wallet.setNonce(nonce);
      wallet.setNonceEncode(encoder.encode(nonce));
      wallet.setExpiryDateNonce(Instant.now().plusMillis(nonceExpirationMs));
      wallet.setUser(user);
      WalletEntity walletSave = walletRepository.save(wallet);
      walletId = walletSave.getId();
    } else {
      walletService.updateNewNonce(walletOpt.get());
      walletId = walletOpt.get().getId();
    }
    String newAccessToken = jwtProvider.generateJwtTokenFromUsername(username, walletId);
    refreshTokenService.revokeRefreshToken(transfersWalletRequest.getRefreshToken());
    RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user.getId(),
        stakeAddress);
    userHistoryService.saveUserHistory(EUserAction.TRANSFERS_WALLET, null, Instant.now(), true,
        stakeAddress, user);
    redisProvider.blacklistJwt(accessToken, username);
    return ResponseEntity.ok(SignInResponse.builder().token(newAccessToken).walletId(walletId)
        .username(user.getUsername()).email(user.getEmail()).tokenType(CommonConstant.TOKEN_TYPE)
        .refreshToken(refreshToken.getToken()).build());
  }

  private Set<RoleEntity> addRoleForUser(ERole eRole) {
    Set<RoleEntity> roles = new HashSet<>();
    switch (eRole) {
      case ROLE_ADMIN:
        RoleEntity rAdmin = roleRepository.findByName(ERole.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
        roles.add(rAdmin);
        break;
      case ROLE_USER:
        RoleEntity rUser = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
        roles.add(rUser);
        break;
      default:
    }
    return roles;
  }
}
