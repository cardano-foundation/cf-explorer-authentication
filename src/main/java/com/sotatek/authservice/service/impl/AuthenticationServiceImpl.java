package com.sotatek.authservice.service.impl;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.ByteString;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.Map;
import co.nstant.in.cbor.model.UnicodeString;
import com.bloxbean.cardano.client.util.HexUtil;
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
import com.sotatek.authservice.service.UserService;
import com.sotatek.authservice.service.WalletService;
import com.sotatek.authservice.util.NonceUtils;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.TokenRefreshException;
import com.sotatek.cardanocommonapi.exceptions.ValidSignatureException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import com.sotatek.cardanocommonapi.utils.StringUtils;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
  private UserService userService;

  @Autowired
  private WalletService walletService;

  @Autowired
  private PasswordEncoder encoder;

  @Override
  public void blacklistJwt(String token, String username) {
    if (!isTokenBlacklisted(token)) {
      redisTemplate.opsForValue().set(RedisConstant.JWT + token, username);
    }
  }

  @Override
  public boolean isTokenBlacklisted(String token) {
    if (Boolean.TRUE.equals(StringUtils.isNullOrEmpty(token))) {
      throw BusinessException.builder()
          .errorCode(CommonErrorCode.INVALID_TOKEN.getServiceErrorCode())
          .errorMsg(CommonErrorCode.INVALID_TOKEN.getDesc()).build();
    }
    return redisTemplate.opsForValue().get(RedisConstant.JWT + token) != null;
  }

  @Override
  public ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest) {
    try {
      String signature = signInRequest.getSignature();
      String ipAddress = signInRequest.getIpAddress();
      String stakeAddress = signInRequest.getStakeAddress();
      List<DataItem> itemList = CborDecoder.decode(HexUtil.decodeHexString(signature));
      List<DataItem> topArray = ((Array) itemList.get(0)).getDataItems();
      ByteString messageToSign = (ByteString) topArray.get(2);
      byte[] message = messageToSign.getBytes();
      String nonceCheck = new String(message);
//      ByteString protectedHeader = (ByteString) topArray.get(0);
//      List<DataItem> protectedHeaderMapDIList = CborDecoder.decode(protectedHeader.getBytes());
//      Map protectedHeaderMap = (Map) protectedHeaderMapDIList.get(0);
//      ByteString address = (ByteString) protectedHeaderMap.get(new UnicodeString("address"));
//      String hexAddress = HexUtil.encodeHexString(address.getBytes());
      // Find the nonce value from the DB that was used to sign this message
      Optional<UserEntity> userOpt = userRepository.findByStakeAddress(stakeAddress);
      if (userOpt.isEmpty()) {
        throw BusinessException.builder()
            .errorCode(CommonErrorCode.USER_IS_NOT_EXIST.getServiceErrorCode())
            .errorMsg(CommonErrorCode.USER_IS_NOT_EXIST.getDesc()).build();
      }
      UserEntity user = userOpt.get();
      Optional<WalletEntity> walletOpt = walletRepository.findByStakeAddress(stakeAddress);
      WalletEntity wallet = walletOpt.get();
      if (wallet.getExpiryDateNonce().compareTo(Instant.now()) < 0) {
        walletService.updateNewNonce(wallet);
        throw BusinessException.builder()
            .errorCode(CommonErrorCode.NONCE_EXPIRED.getServiceErrorCode())
            .errorMsg(CommonErrorCode.NONCE_EXPIRED.getDesc()).build();
      }
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(stakeAddress, nonceCheck));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtProvider.generateJwtToken(authentication);
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList());
      RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(),
          jwt);
      userHistoryService.saveUserHistory(EUserAction.LOGIN, ipAddress, Instant.now(), true,
          user.getUsername());
      walletService.updateNewNonce(wallet);
      return ResponseEntity.ok(
          SignInResponse.builder().token(jwt).id(userDetails.getId()).username(user.getUsername())
              .email(userDetails.getEmail()).role(roles).tokenType("Bearer")
              .refreshToken(refreshToken.getToken()).build());
    } catch (AuthenticationException ex) {
      throw ValidSignatureException.builder()
          .errCode(CommonErrorCode.SIGNATURE_INVALID.getServiceErrorCode())
          .errMessage(CommonErrorCode.SIGNATURE_INVALID.getDesc()).build();
    } catch (CborException ex) {
      throw BusinessException.builder()
          .errorCode(CommonErrorCode.UNKNOWN_ERROR.getServiceErrorCode())
          .errorMsg(CommonErrorCode.UNKNOWN_ERROR.getDesc()).build();
    }
  }

  @Override
  public ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest) {
    String username = signUpRequest.getUsername();
    if (Boolean.TRUE.equals(userRepository.existsByUsername(username))) {
      return ResponseEntity.badRequest()
          .body(new SignUpResponse("Error: Username is already exist!"));
    }
    WalletRequest walletRequest = signUpRequest.getWallet();
    if (Boolean.TRUE.equals(
        walletRepository.existsByStakeAddress(walletRequest.getStakeAddress()))) {
      return ResponseEntity.badRequest()
          .body(new SignUpResponse("Error: Wallet is already in exist!"));
    }
    String nonce = NonceUtils.createNonce();
    UserEntity user = UserEntity.builder().username(signUpRequest.getUsername())
        .email(signUpRequest.getEmail()).phone(signUpRequest.getPhone()).avatar(null).build();
    Set<Integer> setRoles = signUpRequest.getRoles();
    Set<RoleEntity> roles = new HashSet<>();
    if (setRoles == null || setRoles.isEmpty()) {
      RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
      roles.add(userRole);
    } else {
      setRoles.forEach(role -> {
        switch (role) {
          case 1:
            RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(
                () -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
            roles.add(adminRole);
            break;
          case 2:
            RoleEntity modRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(
                () -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
            roles.add(modRole);
            break;
          default:
            //Todo
        }
      });
    }
    user.setRoles(roles);
    UserEntity userSave = userRepository.save(user);
    WalletEntity wallet = WalletEntity.builder().stakeAddress(walletRequest.getStakeAddress())
        .walletName(walletRequest.getWalletName()).balanceAtLogin(walletRequest.getBalanceAtLogin())
        .networkId(walletRequest.getNetworkId()).networkType(walletRequest.getNetworkType())
        .nonce(nonce).nonceEncode(encoder.encode(nonce))
        .expiryDateNonce(Instant.now().plusMillis(nonceExpirationMs)).user(userSave).build();
    walletRepository.save(wallet);
    userHistoryService.saveUserHistory(EUserAction.CREATED, signUpRequest.getIpAddress(),
        Instant.now(), true, user.getUsername());
    return ResponseEntity.ok(
        SignUpResponse.builder().message(username + ": registered successfully!").build());
  }

  @Override
  public ResponseEntity<RefreshTokenResponse> refreshToken(
      RefreshTokenRequest refreshTokenRequest) {
    String tokenOfRefreshToken = refreshTokenRequest.getRefreshToken();
    Optional<RefreshTokenEntity> refreshTokenOpt = refreshTokenService.findByToken(
        tokenOfRefreshToken);
    if (refreshTokenOpt.isEmpty()) {
      throw new TokenRefreshException(tokenOfRefreshToken,
          CommonErrorCode.REFRESH_TOKEN_IS_NOT_EXIST);
    }
    RefreshTokenEntity refreshToken = refreshTokenOpt.get();
    final String accessToken = refreshToken.getAccessToken();
    final String username = refreshToken.getUser().getUsername();
    blacklistJwt(accessToken, username);
    refreshTokenService.verifyExpiration(refreshToken);
    String token = jwtProvider.generateTokenFromUsername(refreshToken.getUser());
    refreshToken.setAccessToken(token);
    refreshTokenService.updateRefreshToken(refreshToken);
    return ResponseEntity.ok(
        RefreshTokenResponse.builder().accessToken(token).refreshToken(tokenOfRefreshToken)
            .tokenType("Bearer").build());
  }

  @Override
  public ResponseEntity<String> signOut(SignOutRequest signOutRequest) {
    String accessToken = signOutRequest.getAccessToken();
    String username = signOutRequest.getUsername();
    String ipAddress = signOutRequest.getIpAddress();
    if (Boolean.TRUE.equals(StringUtils.isNullOrEmpty(accessToken))) {
      throw BusinessException.builder()
          .errorCode(CommonErrorCode.INVALID_TOKEN.getServiceErrorCode())
          .errorMsg(CommonErrorCode.INVALID_TOKEN.getDesc()).build();
    }
    refreshTokenService.revokeRefreshToken(signOutRequest.getRefreshToken());
    userHistoryService.saveUserHistory(EUserAction.LOGOUT, ipAddress, Instant.now(), true,
        username);
    blacklistJwt(accessToken, username);
    return ResponseEntity.ok("User successfully logout");
  }

}
