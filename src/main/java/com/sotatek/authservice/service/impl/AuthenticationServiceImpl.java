package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.constant.CommonConstant;
import com.sotatek.authservice.model.entity.RefreshTokenEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
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
import com.sotatek.authservice.service.AuthenticationService;
import com.sotatek.authservice.service.RefreshTokenService;
import com.sotatek.authservice.service.UserService;
import com.sotatek.authservice.service.WalletService;
import com.sotatek.authservice.util.NonceUtils;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.IgnoreRollbackException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

  @Value("${nonce.expirationMs}")
  private Long nonceExpirationMs;

  private final UserService userService;

  private final AuthenticationManager authenticationManager;

  private final JwtProvider jwtProvider;

  private final RefreshTokenService refreshTokenService;

  private final WalletService walletService;

  private final RedisProvider redisProvider;

  @Transactional(rollbackFor = {RuntimeException.class}, noRollbackFor = {
      IgnoreRollbackException.class})
  @Override
  public SignInResponse signIn(SignInRequest signInRequest) {
    log.info("login with cardano wallet is running...");
    String signature = signInRequest.getSignature();
    String address = signInRequest.getAddress();
    String nonceFromSign = NonceUtils.getNonceFromSignature(signature);
    UserEntity user = userService.findUserByWalletAddress(address);
    WalletEntity wallet = walletService.findWalletByAddress(address);
    if (wallet.getExpiryDateNonce().compareTo(Instant.now()) < 0) {
      log.error("error: nonce value is expired");
      walletService.updateNonce(wallet);
      throw new IgnoreRollbackException(CommonErrorCode.NONCE_EXPIRED);
    }
    Authentication authentication;
    try {
      authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(address, nonceFromSign));
    } catch (AuthenticationException e) {
      log.error("Exception authentication: " + e.getMessage());
      throw new BusinessException(CommonErrorCode.SIGNATURE_INVALID);
    }
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String accessToken = jwtProvider.generateJwtToken(authentication, user.getUsername());
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(wallet);
    walletService.updateNonce(wallet);
    return SignInResponse.builder().token(accessToken).walletId(userDetails.getId())
        .username(user.getUsername()).email(userDetails.getEmail())
        .tokenType(CommonConstant.TOKEN_TYPE).refreshToken(refreshToken.getToken()).build();
  }

  @Transactional(rollbackFor = {RuntimeException.class})
  @Override
  public SignUpResponse signUp(SignUpRequest signUpRequest) {
    String username = signUpRequest.getUsername();
    if (Boolean.TRUE.equals(userService.checkExistUsername(username))) {
      throw new BusinessException(CommonErrorCode.USERNAME_IS_ALREADY_EXIST);
    }
    WalletRequest walletRequest = signUpRequest.getWallet();
    if (Boolean.TRUE.equals(walletService.existsByStakeAddress(walletRequest.getAddress()))) {
      throw new BusinessException(CommonErrorCode.WALLET_IS_ALREADY_EXIST);
    }
    UserEntity user = userService.saveUser(signUpRequest);
    WalletEntity wallet = walletService.savaWallet(walletRequest, user);
    return SignUpResponse.builder().message(CommonConstant.RESPONSE_SUCCESS)
        .nonce(wallet.getNonce()).build();
  }

  @Transactional(rollbackFor = {RuntimeException.class})
  @Override
  public RefreshTokenResponse refreshToken(String refreshJwt,
      HttpServletRequest httpServletRequest) {
    final String accessToken = jwtProvider.parseJwt(httpServletRequest);
    return refreshTokenService.findByRefToken(refreshJwt).map(refreshTokenService::verifyExpiration)
        .map(refToken -> {
          WalletEntity wallet = refToken.getWallet();
          UserEntity user = userService.findUserByWalletAddress(wallet.getAddress());
          redisProvider.blacklistJwt(accessToken, user.getUsername());
          return jwtProvider.generateJwtTokenFromUsername(user.getUsername(), wallet.getId());
        }).map(newAccessToken -> RefreshTokenResponse.builder().accessToken(newAccessToken)
            .refreshToken(refreshJwt).tokenType(CommonConstant.TOKEN_TYPE).build())
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
  }

  @Transactional(rollbackFor = {RuntimeException.class})
  @Override
  public String signOut(SignOutRequest signOutRequest, HttpServletRequest httpServletRequest) {
    String username = signOutRequest.getUsername();
    String refreshToken = signOutRequest.getRefreshToken();
    String accessToken = jwtProvider.parseJwt(httpServletRequest);
    refreshTokenService.revokeRefreshToken(refreshToken);
    redisProvider.blacklistJwt(accessToken, username);
    return CommonConstant.RESPONSE_SUCCESS;
  }

  @Transactional(rollbackFor = {RuntimeException.class})
  @Override
  public SignInResponse transfersWallet(TransfersWalletRequest transfersWalletRequest,
      HttpServletRequest httpServletRequest) {
    String accessToken = jwtProvider.parseJwt(httpServletRequest);
    jwtProvider.validateJwtToken(accessToken);
    String username = transfersWalletRequest.getUsername();
    WalletRequest walletRequest = transfersWalletRequest.getWallet();
    UserEntity user = userService.findByUsername(username);
    WalletEntity currentWallet = walletService.checkTransferWallet(walletRequest.getAddress());
    WalletEntity wallet =
        Objects.isNull(currentWallet) ? walletService.savaWallet(walletRequest, user)
            : walletService.updateNonce(currentWallet);
    Long walletId = wallet.getId();
    String newAccessToken = jwtProvider.generateJwtTokenFromUsername(username, walletId);
    refreshTokenService.revokeRefreshToken(transfersWalletRequest.getRefreshToken());
    RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(wallet);
    redisProvider.blacklistJwt(accessToken, username);
    return SignInResponse.builder().token(newAccessToken).walletId(walletId)
        .username(user.getUsername()).email(user.getEmail()).tokenType(CommonConstant.TOKEN_TYPE)
        .refreshToken(refreshToken.getToken()).build();
  }
}
