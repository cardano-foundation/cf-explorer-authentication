package org.cardanofoundation.authentication.service.impl;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import org.keycloak.representations.idm.UserRepresentation;

import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.exception.BusinessCode;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.model.request.auth.ResetPasswordRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.provider.MailProvider;
import org.cardanofoundation.authentication.service.JwtTokenService;
import org.cardanofoundation.authentication.service.VerifyService;
import org.cardanofoundation.authentication.thread.MailHandler;
import org.cardanofoundation.explorer.common.entity.enumeration.TokenAuthType;

@Service
@RequiredArgsConstructor
@Log4j2
public class VerifyServiceImpl implements VerifyService {

  private final MailProvider mailProvider;

  private final JwtProvider jwtProvider;

  private final ThreadPoolExecutor sendMailExecutor;

  private final KeycloakProvider keycloakProvider;

  private final LocaleResolver localeResolver;

  private final JwtTokenService jwtTokenService;

  @Override
  public MessageResponse checkVerifySignUpByEmail(String code) {
    Boolean isBlacklisted = jwtTokenService.isBlacklistToken(code, TokenAuthType.VERIFY_CODE);
    if (isBlacklisted) {
      return new MessageResponse(BusinessCode.INVALID_VERIFY_CODE);
    }
    Boolean validateCode = jwtProvider.validateVerifyCode(code);
    if (validateCode.equals(Boolean.FALSE)) {
      log.error("Code is invalid: " + code);
      return new MessageResponse(BusinessCode.INVALID_VERIFY_CODE);
    }
    String accountId = jwtProvider.getAccountIdFromVerifyCode(code);
    jwtTokenService.blacklistToken(code, TokenAuthType.VERIFY_CODE);
    UserRepresentation user = keycloakProvider.getUser(accountId);
    if (Objects.nonNull(user)) {
      user.setEnabled(true);
      keycloakProvider.getResource().get(user.getId()).update(user);
    } else {
      log.error("User not found: " + accountId);
      return new MessageResponse(BusinessCode.INVALID_VERIFY_CODE);
    }
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public MessageResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
    String code = resetPasswordRequest.getCode();
    Boolean isBlacklisted = jwtTokenService.isBlacklistToken(code, TokenAuthType.RESET_PASSWORD);
    if (isBlacklisted) {
      return new MessageResponse(BusinessCode.INVALID_VERIFY_CODE);
    }
    Boolean validateCode = jwtProvider.validateVerifyCode(code);
    if (validateCode.equals(Boolean.FALSE)) {
      log.error("Code is invalid: " + code);
      return new MessageResponse(BusinessCode.INVALID_VERIFY_CODE);
    }
    String accountId = jwtProvider.getAccountIdFromVerifyCode(code);
    jwtTokenService.blacklistToken(code, TokenAuthType.RESET_PASSWORD);
    UserRepresentation user = keycloakProvider.getUser(accountId);
    user.setCredentials(
        Collections.singletonList(
            keycloakProvider.createPasswordCredentials(resetPasswordRequest.getPassword())));
    keycloakProvider.getResource().get(user.getId()).update(user);
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public MessageResponse forgotPassword(String email, HttpServletRequest httpServletRequest) {
    UserRepresentation user = keycloakProvider.getUser(email);
    if (Objects.isNull(user)) {
      return new MessageResponse(CommonConstant.CODE_FAILURE, CommonConstant.RESPONSE_FAILURE);
    }
    String code = jwtProvider.generateCodeForVerify(email);
    sendMailExecutor.execute(
        new MailHandler(
            mailProvider,
            email,
            EUserAction.RESET_PASSWORD,
            localeResolver.resolveLocale(httpServletRequest),
            code));
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public Boolean checkExpiredCode(String code) {
    Boolean isBlacklistedVerifyCode =
        jwtTokenService.isBlacklistToken(code, TokenAuthType.VERIFY_CODE);
    Boolean isBlackListedResetToken =
        jwtTokenService.isBlacklistToken(code, TokenAuthType.RESET_PASSWORD);
    if (isBlacklistedVerifyCode || isBlackListedResetToken) {
      return false;
    }
    return jwtProvider.validateVerifyCode(code);
  }
}
