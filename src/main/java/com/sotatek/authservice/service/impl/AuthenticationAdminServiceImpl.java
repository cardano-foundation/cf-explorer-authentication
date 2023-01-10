package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.constant.CommonConstant;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.EStatus;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.request.admin.SignUpAdminRequest;
import com.sotatek.authservice.model.response.MessageResponse;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.provider.MailProvider;
import com.sotatek.authservice.service.AuthenticationAdminService;
import com.sotatek.authservice.service.UserService;
import com.sotatek.authservice.thread.MailHandler;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationAdminServiceImpl implements AuthenticationAdminService {

  private final UserService userService;

  private final MailProvider mailProvider;

  private final PasswordEncoder encoder;

  private final JwtProvider jwtProvider;

  private final ThreadPoolExecutor sendMailExecutor;

  @Override
  public MessageResponse signUp(SignUpAdminRequest signUpAdmin) {
    String username = signUpAdmin.getUsername();
    if (Boolean.TRUE.equals(userService.checkExistUsername(username))) {
      throw new BusinessException(CommonErrorCode.USERNAME_IS_ALREADY_EXIST);
    }
    String email = signUpAdmin.getEmail();
    if (Boolean.TRUE.equals(userService.checkExistEmail(email))) {
      throw new BusinessException(CommonErrorCode.EMAIL_IS_ALREADY_EXIST);
    }
    signUpAdmin.setPassword(encoder.encode(signUpAdmin.getPassword()));
    UserEntity user = userService.saveUserAdmin(signUpAdmin);
    String code = jwtProvider.generateJwtForVerifyAdmin(user.getUsername());
    sendMailExecutor.execute(new MailHandler(mailProvider, user, EUserAction.CREATED, code));
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public MessageResponse checkVerifySignUpByEmail(String code) {
    Boolean validateCode = jwtProvider.validateVerifyCode(code);
    if (validateCode.equals(Boolean.FALSE)) {
      return new MessageResponse(CommonErrorCode.INVALID_VERIFY_CODE);
    }
    String username = jwtProvider.getUserNameFromVerifyCode(code);
    UserEntity user = userService.findByUsername(username);
    if (user.getStatus().equals(EStatus.PENDING)) {
      userService.activeUserAdmin(username);
    } else {
      return new MessageResponse(CommonErrorCode.VERIFY_CODE_NOT_PENDING);
    }
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }
}
