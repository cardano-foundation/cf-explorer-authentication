package org.cardanofoundation.authentication.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.enums.EStatus;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.model.request.auth.ResetPasswordRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.MailProvider;
import org.cardanofoundation.authentication.provider.RedisProvider;
import org.cardanofoundation.authentication.repository.UserRepository;
import org.cardanofoundation.authentication.service.impl.VerifyServiceImpl;
import org.cardanofoundation.authentication.thread.MailHandler;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class VerifyServiceTest {

  @InjectMocks
  private VerifyServiceImpl verifyService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserService userService;

  @Mock
  private MailProvider mailProvider;

  @Mock
  private PasswordEncoder encoder;

  @Mock
  private JwtProvider jwtProvider;

  @Mock
  private RedisProvider redisProvider;

  @Mock
  private ThreadPoolExecutor sendMailExecutor;

  @Mock
  private LocaleResolver localeResolver;

  private final String CODE = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzb3RhdGVrIiwianRpIjoiMiIsImlhdCI6MTY3Mjk5Nzc0MSwiZXhwIjoxNjczMDg0MTQxfQ.B62gXo6iqQfHMT62q17zdhwMF8I77-P6xblKcx7ZI3-gij6YckvFYVVuoIa_qXgTTFnEeRDBQEVo3o20D1w6pffBrgbvxvMbjhOG0ONS9Xs1UQChwQs7v3lxkqoKZ8dNf0Eib43HxLZhBEBIeXa1kln4sS8osWf5iEgno0od7z9KwWK1N2Coj0o-1HE453fFyRveDJgd0DvXohbHADMmjH9t0WkXJwUK26Lv1tkqPlkIzGBPgYnYEIygdayqqt4EtP6CtgI9QOzCYSZUUFzxo-VVDzA0J7DpQbYn8G2PAuAbCXCO6lTkvmXMiyZAoZshqRhBNb7uDI66dwOJLV3NzuunSa8QOO8eNUaDoHHvR_9_J-yHTFBicoM69JHQ7UzJVyFHGmh1M8lHsJ9y6DdAobtBSyJFBhFeDj7S8bgpIvIyNoHDsf24xdlqCngE1qBsxjfp0L_yMPBxsIhW3Juopwe1c6btWTEaRaVaxhKE5yKbRsTtAzDDkdEyg_--9eXH";

  private final String EMAIL = "test@gmail.com";

  private final String PASSWORD_ENCODE = "$2a$10$4egM5wLBPz7zpVlxFL8CguVLQacZ8I6yRhxW1iqUkMVEFUHK2s7Oa";

  @Test
  void whenCheckVerifySignUpByEmail_codeInValid1_throwException() {
    when(redisProvider.isTokenBlacklisted(CODE)).thenReturn(true);
    MessageResponse response = verifyService.checkVerifySignUpByEmail(CODE);
    String expectedCode = CommonErrorCode.INVALID_VERIFY_CODE.getServiceErrorCode();
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenCheckVerifySignUpByEmail_codeInValid2_throwException() {
    when(redisProvider.isTokenBlacklisted(CODE)).thenReturn(false);
    when(jwtProvider.validateVerifyCode(CODE)).thenReturn(false);
    MessageResponse response = verifyService.checkVerifySignUpByEmail(CODE);
    String expectedCode = CommonErrorCode.INVALID_VERIFY_CODE.getServiceErrorCode();
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenCheckVerifySignUpByEmail_statusNotPending_throwException() {
    when(redisProvider.isTokenBlacklisted(CODE)).thenReturn(false);
    when(jwtProvider.validateVerifyCode(CODE)).thenReturn(true);
    when(jwtProvider.getAccountIdFromVerifyCode(CODE)).thenReturn(EMAIL);
    doNothing().when(redisProvider).blacklistJwt(CODE, EMAIL);
    when(userService.findByEmailAndStatus(EMAIL, EStatus.PENDING)).thenReturn(null);
    MessageResponse response = verifyService.checkVerifySignUpByEmail(CODE);
    String expectedCode = CommonErrorCode.VERIFY_CODE_NOT_PENDING.getServiceErrorCode();
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenCheckVerifySignUpByEmail_statusPending_returnSuccess() {
    UserEntity user = UserEntity.builder().email(EMAIL).build();
    when(redisProvider.isTokenBlacklisted(CODE)).thenReturn(false);
    when(jwtProvider.validateVerifyCode(CODE)).thenReturn(true);
    when(jwtProvider.getAccountIdFromVerifyCode(CODE)).thenReturn(EMAIL);
    doNothing().when(redisProvider).blacklistJwt(CODE, EMAIL);
    when(userService.findByEmailAndStatus(EMAIL, EStatus.PENDING)).thenReturn(user);
    doNothing().when(userService).activeUser(EMAIL);
    MessageResponse response = verifyService.checkVerifySignUpByEmail(CODE);
    String expectedCode = CommonConstant.CODE_SUCCESS;
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenResetPassword_codeInValid1_throwException() {
    ResetPasswordRequest request = new ResetPasswordRequest();
    request.setCode(CODE);
    request.setPassword("password");
    when(redisProvider.isTokenBlacklisted(CODE)).thenReturn(true);
    MessageResponse response = verifyService.resetPassword(request);
    String expectedCode = CommonErrorCode.INVALID_VERIFY_CODE.getServiceErrorCode();
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenResetPassword_codeInValid2_throwException() {
    ResetPasswordRequest request = new ResetPasswordRequest();
    request.setCode(CODE);
    request.setPassword("password");
    when(redisProvider.isTokenBlacklisted(CODE)).thenReturn(false);
    when(jwtProvider.validateVerifyCode(CODE)).thenReturn(false);
    MessageResponse response = verifyService.resetPassword(request);
    String expectedCode = CommonErrorCode.INVALID_VERIFY_CODE.getServiceErrorCode();
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenResetPassword_userInValid_throwException() {
    ResetPasswordRequest request = new ResetPasswordRequest();
    request.setCode(CODE);
    request.setPassword("password");
    when(redisProvider.isTokenBlacklisted(CODE)).thenReturn(false);
    when(jwtProvider.validateVerifyCode(CODE)).thenReturn(true);
    when(jwtProvider.getAccountIdFromVerifyCode(CODE)).thenReturn(EMAIL);
    doNothing().when(redisProvider).blacklistJwt(CODE, EMAIL);
    when(userService.findByEmailAndStatus(EMAIL, EStatus.ACTIVE)).thenReturn(null);
    MessageResponse response = verifyService.resetPassword(request);
    String expectedCode = CommonConstant.CODE_FAILURE;
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenResetPassword_returnResponse() {
    ResetPasswordRequest request = new ResetPasswordRequest();
    request.setCode(CODE);
    request.setPassword("password");
    UserEntity user = UserEntity.builder().build();
    when(redisProvider.isTokenBlacklisted(CODE)).thenReturn(false);
    when(jwtProvider.validateVerifyCode(CODE)).thenReturn(true);
    when(jwtProvider.getAccountIdFromVerifyCode(CODE)).thenReturn(EMAIL);
    doNothing().when(redisProvider).blacklistJwt(CODE, EMAIL);
    when(userService.findByEmailAndStatus(EMAIL, EStatus.ACTIVE)).thenReturn(user);
    when(encoder.encode("password")).thenReturn(PASSWORD_ENCODE);
    when(userRepository.save(any())).thenReturn(user);
    MessageResponse response = verifyService.resetPassword(request);
    String expectedCode = CommonConstant.CODE_SUCCESS;
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenForgotPassword_userInValid_throwException() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(userRepository.findByEmailAndStatus(EMAIL, EStatus.ACTIVE)).thenReturn(Optional.empty());
    MessageResponse response = verifyService.forgotPassword(EMAIL, httpServletRequest);
    String expectedCode = CommonConstant.CODE_FAILURE;
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenForgotPassword_returnResponse() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    UserEntity user = UserEntity.builder().build();
    when(userRepository.findByEmailAndStatus(EMAIL, EStatus.ACTIVE)).thenReturn(Optional.of(user));
    when(jwtProvider.generateCodeForVerify(EMAIL)).thenReturn(CODE);
    doNothing().when(sendMailExecutor)
        .execute(new MailHandler(mailProvider, user, EUserAction.RESET_PASSWORD, any(), CODE));
    MessageResponse response = verifyService.forgotPassword(EMAIL, httpServletRequest);
    String expectedCode = CommonConstant.CODE_SUCCESS;
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }
}
