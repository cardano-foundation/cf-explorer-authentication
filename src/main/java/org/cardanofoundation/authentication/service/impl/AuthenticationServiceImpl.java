package org.cardanofoundation.authentication.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.entity.RefreshTokenEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.entity.WalletEntity;
import org.cardanofoundation.authentication.model.entity.security.UserDetailsImpl;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.model.enums.EWalletName;
import org.cardanofoundation.authentication.model.request.auth.SignInRequest;
import org.cardanofoundation.authentication.model.request.auth.SignOutRequest;
import org.cardanofoundation.authentication.model.request.auth.SignUpRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.auth.NonceResponse;
import org.cardanofoundation.authentication.model.response.auth.RefreshTokenResponse;
import org.cardanofoundation.authentication.model.response.auth.SignInResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.MailProvider;
import org.cardanofoundation.authentication.provider.RedisProvider;
import org.cardanofoundation.authentication.repository.WalletRepository;
import org.cardanofoundation.authentication.service.AuthenticationService;
import org.cardanofoundation.authentication.service.RefreshTokenService;
import org.cardanofoundation.authentication.service.UserService;
import org.cardanofoundation.authentication.service.WalletService;
import org.cardanofoundation.authentication.thread.MailHandler;
import org.cardanofoundation.authentication.util.NonceUtils;
import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.IgnoreRollbackException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
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
    String accountId = "";
    String password = "";
    WalletEntity wallet = null;
    Integer type = signInRequest.getType();
    if (type == 0) {
      log.info("login with email and password...");
      accountId = signInRequest.getEmail();
      password = signInRequest.getPassword();
    } else {
      log.info("login with cardano wallet...");
      accountId = signInRequest.getAddress();
      password = NonceUtils.getNonceFromSignature(signInRequest.getSignature());
      wallet = walletRepository.findWalletByAddress(signInRequest.getAddress())
          .orElseThrow(() -> new BusinessException(CommonErrorCode.WALLET_IS_NOT_EXIST));
      if (wallet.getExpiryDateNonce().compareTo(Instant.now()) < 0) {
        log.error("error: nonce value is expired");
        walletService.updateNonce(wallet);
        throw new IgnoreRollbackException(CommonErrorCode.NONCE_EXPIRED);
      }
    }
    Authentication authentication;
    try {
      authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(accountId, password));
    } catch (AuthenticationException e) {
      log.error("Exception authentication: " + e.getMessage());
      if (type == 0) {
        throw new BusinessException(CommonErrorCode.USERNAME_OR_PASSWORD_INVALID);
      } else {
        throw new BusinessException(CommonErrorCode.SIGNATURE_INVALID);
      }
    }
    UserEntity user = userService.findByAccountId(accountId);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String accessToken = jwtProvider.generateJwtToken(authentication, accountId);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    RefreshTokenEntity refreshToken = refreshTokenService.addRefreshToken(user);
    if (Objects.nonNull(wallet)) {
      walletService.updateNonce(wallet);
    }
    return SignInResponse.builder().token(accessToken).address(signInRequest.getAddress())
        .email(userDetails.getEmail()).tokenType(CommonConstant.TOKEN_TYPE)
        .refreshToken(refreshToken.getToken()).build();
  }

  @Transactional(rollbackFor = RuntimeException.class)
  @Override
  public MessageResponse signUp(SignUpRequest signUpRequest) {
    String email = signUpRequest.getEmail();
    if (Boolean.TRUE.equals(userService.checkExistEmail(email))) {
      throw new BusinessException(CommonErrorCode.EMAIL_IS_ALREADY_EXIST);
    }
    signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
    UserEntity user = userService.saveUser(signUpRequest);
    String verifyCode = jwtProvider.generateCodeForVerify(email);
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
          String address = walletRepository.findAddressByUserId(user.getId());
          String accountId = Objects.isNull(address) ? user.getEmail() : address;
          redisProvider.blacklistJwt(accessToken, accountId);
          return jwtProvider.generateJwtToken(user, accountId);
        }).map(newAccessToken -> RefreshTokenResponse.builder().accessToken(newAccessToken)
            .refreshToken(refreshJwt).tokenType(CommonConstant.TOKEN_TYPE).build())
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
  }

  @Override
  public MessageResponse signOut(SignOutRequest signOutRequest,
      HttpServletRequest httpServletRequest) {
    String refreshJwt = signOutRequest.getRefreshJwt();
    String accessToken = jwtProvider.parseJwt(httpServletRequest);
    refreshTokenService.revokeRefreshToken(refreshJwt);
    redisProvider.blacklistJwt(accessToken, signOutRequest.getAccountId());
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
