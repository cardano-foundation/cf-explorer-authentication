package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.constant.CommonConstant;
import com.sotatek.authservice.model.entity.RefreshTokenEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.enums.EWalletName;
import com.sotatek.authservice.model.request.auth.SignInRequest;
import com.sotatek.authservice.model.request.auth.SignOutRequest;
import com.sotatek.authservice.model.request.auth.SignUpRequest;
import com.sotatek.authservice.model.response.MessageResponse;
import com.sotatek.authservice.model.response.auth.NonceResponse;
import com.sotatek.authservice.model.response.auth.RefreshTokenResponse;
import com.sotatek.authservice.model.response.auth.SignInResponse;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.provider.MailProvider;
import com.sotatek.authservice.provider.RedisProvider;
import com.sotatek.authservice.repository.WalletRepository;
import com.sotatek.authservice.service.AuthenticationService;
import com.sotatek.authservice.service.RefreshTokenService;
import com.sotatek.authservice.service.UserService;
import com.sotatek.authservice.service.WalletService;
import com.sotatek.authservice.thread.MailHandler;
import com.sotatek.authservice.util.NonceUtils;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.IgnoreRollbackException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

  private final UserService userService;

  private final AuthenticationManager authenticationManager;

  private final JwtProvider jwtProvider;

  private final RefreshTokenService refreshTokenService;

  private final WalletService walletService;

  private final RedisProvider redisProvider;

  private final WalletRepository walletRepository;

  private final MailProvider mailProvider;

  private final ThreadPoolExecutor sendMailExecutor;

  private final PasswordEncoder encoder;

  @Transactional(rollbackFor = {RuntimeException.class}, noRollbackFor = {
      IgnoreRollbackException.class})
  @Override
  public SignInResponse signIn(SignInRequest signInRequest) {
    log.info("login is running...");
    String username = "";
    String password = "";
    WalletEntity wallet = null;
    Integer type = signInRequest.getType();
    if (type == 0) {
      log.info("login with username and password...");
      username = signInRequest.getUsername();
      password = signInRequest.getPassword();
    } else {
      log.info("login with cardano wallet...");
      username = signInRequest.getAddress();
      password = NonceUtils.getNonceFromSignature(signInRequest.getSignature());
      wallet = walletService.findWalletByAddress(signInRequest.getAddress());
      if (wallet.getExpiryDateNonce().compareTo(Instant.now()) < 0) {
        log.error("error: nonce value is expired");
        walletService.updateNonce(wallet);
        throw new IgnoreRollbackException(CommonErrorCode.NONCE_EXPIRED);
      }
    }
    Authentication authentication;
    try {
      authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (AuthenticationException e) {
      log.error("Exception authentication: " + e.getMessage());
      throw new BusinessException(CommonErrorCode.SIGNATURE_INVALID);
    }
    UserEntity user = userService.findByUsername(username);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String accessToken = jwtProvider.generateJwtToken(authentication, username);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    RefreshTokenEntity refreshToken = refreshTokenService.addRefreshToken(user);
    if (Objects.nonNull(wallet)) {
      walletService.updateNonce(wallet);
    }
    return SignInResponse.builder().token(accessToken).username(username)
        .email(userDetails.getEmail()).tokenType(CommonConstant.TOKEN_TYPE)
        .refreshToken(refreshToken.getToken()).build();
  }

  @Transactional(rollbackFor = RuntimeException.class)
  @Override
  public MessageResponse signUp(SignUpRequest signUpRequest) {
    String username = signUpRequest.getUsername();
    String email = signUpRequest.getEmail();
    if (Boolean.TRUE.equals(userService.checkExistUsername(username))) {
      throw new BusinessException(CommonErrorCode.USERNAME_IS_ALREADY_EXIST);
    }
    if (Boolean.TRUE.equals(userService.checkExistEmail(email))) {
      throw new BusinessException(CommonErrorCode.EMAIL_IS_ALREADY_EXIST);
    }
    signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
    UserEntity user = userService.saveUser(signUpRequest);
    String verifyCode = jwtProvider.generateCodeForVerify(user.getUsername());
    sendMailExecutor.execute(new MailHandler(mailProvider, user, EUserAction.CREATED, verifyCode));
    return MessageResponse.builder().code(CommonConstant.CODE_SUCCESS)
        .message(CommonConstant.RESPONSE_SUCCESS).build();
  }

  @Override
  public RefreshTokenResponse refreshToken(String refreshJwt,
      HttpServletRequest httpServletRequest) {
    final String accessToken = jwtProvider.parseJwt(httpServletRequest);
    return refreshTokenService.findByRefToken(refreshJwt).map(refreshTokenService::verifyExpiration)
        .map(refToken -> {
          UserEntity user = refToken.getUser();
          redisProvider.blacklistJwt(accessToken, user.getUsername());
          return jwtProvider.generateJwtTokenFromUser(user);
        }).map(newAccessToken -> RefreshTokenResponse.builder().accessToken(newAccessToken)
            .refreshToken(refreshJwt).tokenType(CommonConstant.TOKEN_TYPE).build())
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
  }

  @Override
  public MessageResponse signOut(SignOutRequest signOutRequest,
      HttpServletRequest httpServletRequest) {
    String username = signOutRequest.getUsername();
    String refreshJwt = signOutRequest.getRefreshJwt();
    String accessToken = jwtProvider.parseJwt(httpServletRequest);
    refreshTokenService.revokeRefreshToken(refreshJwt);
    redisProvider.blacklistJwt(accessToken, username);
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public NonceResponse findNonceByAddress(String address, EWalletName walletName) {
    Optional<WalletEntity> walletOpt = walletRepository.findWalletByAddress(address);
    if (walletOpt.isPresent()) {
      WalletEntity wallet = walletOpt.get();
      if (wallet.getExpiryDateNonce().compareTo(Instant.now()) < 0) {
        wallet = walletService.updateNonce(wallet);
      }
      return NonceResponse.builder().message(CommonConstant.CODE_SUCCESS)
          .nonce(wallet.getNonce()).build();
    }
    UserEntity user = userService.saveUser(address);
    WalletEntity wallet = walletService.saveWallet(address, user, walletName);
    return NonceResponse.builder().message(CommonConstant.CODE_FAILURE)
        .nonce(wallet.getNonce()).build();
  }
}
