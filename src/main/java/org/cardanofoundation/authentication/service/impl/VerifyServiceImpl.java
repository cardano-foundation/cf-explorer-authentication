package org.cardanofoundation.authentication.service.impl;

import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.enums.EStatus;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.model.request.admin.ResetPasswordRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.MailProvider;
import org.cardanofoundation.authentication.provider.RedisProvider;
import org.cardanofoundation.authentication.repository.UserRepository;
import org.cardanofoundation.authentication.service.UserService;
import org.cardanofoundation.authentication.service.VerifyService;
import org.cardanofoundation.authentication.thread.MailHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class VerifyServiceImpl implements VerifyService {

  private final UserRepository userRepository;

  private final UserService userService;

  private final MailProvider mailProvider;

  private final PasswordEncoder encoder;

  private final JwtProvider jwtProvider;

  private final RedisProvider redisProvider;

  private final ThreadPoolExecutor sendMailExecutor;

  @Override
  public MessageResponse checkVerifySignUpByEmail(String code) {
    if (redisProvider.isTokenBlacklisted(code)) {
      return new MessageResponse(CommonErrorCode.INVALID_VERIFY_CODE);
    }
    Boolean validateCode = jwtProvider.validateVerifyCode(code);
    if (validateCode.equals(Boolean.FALSE)) {
      return new MessageResponse(CommonErrorCode.INVALID_VERIFY_CODE);
    }
    String username = jwtProvider.getUserNameFromVerifyCode(code);
    redisProvider.blacklistJwt(code, username);
    UserEntity user = userService.findByUsernameAndStatus(username, EStatus.PENDING);
    if (Objects.nonNull(user)) {
      userService.activeUser(username);
    } else {
      return new MessageResponse(CommonErrorCode.VERIFY_CODE_NOT_PENDING);
    }
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public MessageResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
    String code = resetPasswordRequest.getCode();
    if (redisProvider.isTokenBlacklisted(code)) {
      return new MessageResponse(CommonErrorCode.INVALID_VERIFY_CODE);
    }
    Boolean validateCode = jwtProvider.validateVerifyCode(code);
    if (validateCode.equals(Boolean.FALSE)) {
      return new MessageResponse(CommonErrorCode.INVALID_VERIFY_CODE);
    }
    String username = jwtProvider.getUserNameFromVerifyCode(code);
    redisProvider.blacklistJwt(code, username);
    UserEntity user = userService.findByUsernameAndStatus(username, EStatus.ACTIVE);
    if (Objects.isNull(user)) {
      return new MessageResponse(CommonConstant.CODE_FAILURE, CommonConstant.RESPONSE_FAILURE);
    }
    user.setPassword(encoder.encode(resetPasswordRequest.getPassword()));
    userRepository.save(user);
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public MessageResponse forgotPassword(String email) {
    UserEntity user = userRepository.findByEmailAndStatus(email, EStatus.ACTIVE).orElse(null);
    if (Objects.isNull(user)) {
      return new MessageResponse(CommonConstant.CODE_FAILURE, CommonConstant.RESPONSE_FAILURE);
    }
    String code = jwtProvider.generateCodeForVerify(user.getUsername());
    sendMailExecutor.execute(new MailHandler(mailProvider, user, EUserAction.RESET_PASSWORD, code));
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }
}
