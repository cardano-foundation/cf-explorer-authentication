package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.constant.CommonConstant;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.EStatus;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.request.admin.ResetPasswordRequest;
import com.sotatek.authservice.model.response.MessageResponse;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.provider.MailProvider;
import com.sotatek.authservice.provider.RedisProvider;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.service.UserService;
import com.sotatek.authservice.service.VerifyService;
import com.sotatek.authservice.thread.MailHandler;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
