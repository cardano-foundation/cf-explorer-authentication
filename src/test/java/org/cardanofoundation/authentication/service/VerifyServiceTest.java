package org.cardanofoundation.authentication.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.concurrent.ThreadPoolExecutor;

import jakarta.servlet.http.HttpServletRequest;

import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.exception.BusinessCode;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.model.request.auth.ResetPasswordRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.provider.MailProvider;
import org.cardanofoundation.authentication.service.impl.VerifyServiceImpl;
import org.cardanofoundation.authentication.thread.MailHandler;
import org.cardanofoundation.explorer.common.entity.explorer.TokenAuth;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class VerifyServiceTest {

  @InjectMocks private VerifyServiceImpl verifyService;

  @Mock private KeycloakProvider keycloakProvider;

  @Mock private MailProvider mailProvider;

  @Mock private JwtProvider jwtProvider;

  @Mock private JwtTokenService jwtTokenService;

  @Mock private ThreadPoolExecutor sendMailExecutor;

  private final String CODE =
      "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzb3RhdGVrIiwianRpIjoiMiIsImlhdCI6MTY3Mjk5Nzc0MSwiZXhwIjoxNjczMDg0MTQxfQ.B62gXo6iqQfHMT62q17zdhwMF8I77-P6xblKcx7ZI3-gij6YckvFYVVuoIa_qXgTTFnEeRDBQEVo3o20D1w6pffBrgbvxvMbjhOG0ONS9Xs1UQChwQs7v3lxkqoKZ8dNf0Eib43HxLZhBEBIeXa1kln4sS8osWf5iEgno0od7z9KwWK1N2Coj0o-1HE453fFyRveDJgd0DvXohbHADMmjH9t0WkXJwUK26Lv1tkqPlkIzGBPgYnYEIygdayqqt4EtP6CtgI9QOzCYSZUUFzxo-VVDzA0J7DpQbYn8G2PAuAbCXCO6lTkvmXMiyZAoZshqRhBNb7uDI66dwOJLV3NzuunSa8QOO8eNUaDoHHvR_9_J-yHTFBicoM69JHQ7UzJVyFHGmh1M8lHsJ9y6DdAobtBSyJFBhFeDj7S8bgpIvIyNoHDsf24xdlqCngE1qBsxjfp0L_yMPBxsIhW3Juopwe1c6btWTEaRaVaxhKE5yKbRsTtAzDDkdEyg_--9eXH";

  private final String EMAIL = "test@gmail.com";

  @Test
  void whenCheckVerifySignUpByEmail_codeInValid1_throwException() {
    when(jwtTokenService.findByToken(anyString(), any()))
        .thenReturn(TokenAuth.builder().blackList(true).build());
    MessageResponse response = verifyService.checkVerifySignUpByEmail(CODE);
    String expectedCode = BusinessCode.INVALID_VERIFY_CODE.getServiceErrorCode();
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenCheckVerifySignUpByEmail_codeInValid2_throwException() {
    when(jwtTokenService.findByToken(anyString(), any()))
        .thenReturn(TokenAuth.builder().blackList(false).build());
    when(jwtProvider.validateVerifyCode(CODE)).thenReturn(false);
    MessageResponse response = verifyService.checkVerifySignUpByEmail(CODE);
    String expectedCode = BusinessCode.INVALID_VERIFY_CODE.getServiceErrorCode();
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenCheckVerifySignUpByEmail_isNotExist_throwException() {
    when(jwtTokenService.findByToken(anyString(), any()))
        .thenReturn(TokenAuth.builder().blackList(false).build());
    when(jwtProvider.validateVerifyCode(CODE)).thenReturn(true);
    when(jwtProvider.getAccountIdFromVerifyCode(CODE)).thenReturn(EMAIL);
    when(keycloakProvider.getUser(EMAIL)).thenReturn(null);
    MessageResponse response = verifyService.checkVerifySignUpByEmail(CODE);
    String expectedCode = BusinessCode.INVALID_VERIFY_CODE.getServiceErrorCode();
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenCheckVerifySignUpByEmail_returnSuccess() {
    when(jwtTokenService.findByToken(anyString(), any()))
        .thenReturn(TokenAuth.builder().blackList(false).build());
    when(jwtProvider.validateVerifyCode(CODE)).thenReturn(true);
    when(jwtProvider.getAccountIdFromVerifyCode(CODE)).thenReturn(EMAIL);
    UserRepresentation user = new UserRepresentation();
    user.setId(EMAIL);
    user.setEnabled(false);
    when(keycloakProvider.getUser(EMAIL)).thenReturn(user);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(EMAIL)).thenReturn(userResource);
    doNothing().when(userResource).update(any());
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
    when(jwtTokenService.findByToken(anyString(), any()))
        .thenReturn(TokenAuth.builder().blackList(true).build());
    MessageResponse response = verifyService.resetPassword(request);
    String expectedCode = BusinessCode.INVALID_VERIFY_CODE.getServiceErrorCode();
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenResetPassword_codeInValid2_throwException() {
    ResetPasswordRequest request = new ResetPasswordRequest();
    request.setCode(CODE);
    request.setPassword("password");
    when(jwtTokenService.findByToken(anyString(), any()))
        .thenReturn(TokenAuth.builder().blackList(false).build());
    when(jwtProvider.validateVerifyCode(CODE)).thenReturn(false);
    MessageResponse response = verifyService.resetPassword(request);
    String expectedCode = BusinessCode.INVALID_VERIFY_CODE.getServiceErrorCode();
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenResetPassword_returnResponse() {
    ResetPasswordRequest request = new ResetPasswordRequest();
    request.setCode(CODE);
    request.setPassword("password");
    when(jwtTokenService.findByToken(anyString(), any()))
        .thenReturn(TokenAuth.builder().blackList(false).build());
    when(jwtProvider.validateVerifyCode(CODE)).thenReturn(true);
    when(jwtProvider.getAccountIdFromVerifyCode(CODE)).thenReturn(EMAIL);
    UserRepresentation user = new UserRepresentation();
    user.setId(EMAIL);
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue("password");
    when(keycloakProvider.createPasswordCredentials("password")).thenReturn(passwordCredentials);
    when(keycloakProvider.getUser(EMAIL)).thenReturn(user);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(EMAIL)).thenReturn(userResource);
    doNothing().when(userResource).update(user);
    MessageResponse response = verifyService.resetPassword(request);
    String expectedCode = CommonConstant.CODE_SUCCESS;
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenForgotPassword_userInValid_throwException() {
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    when(keycloakProvider.getUser(EMAIL)).thenReturn(null);
    MessageResponse response = verifyService.forgotPassword(EMAIL, httpServletRequest);
    String expectedCode = CommonConstant.CODE_FAILURE;
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  @Disabled
  void whenForgotPassword_returnResponse() {
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    UserRepresentation user = new UserRepresentation();
    when(keycloakProvider.getUser(EMAIL)).thenReturn(user);
    when(jwtProvider.generateCodeForVerify(EMAIL)).thenReturn(CODE);
    doNothing()
        .when(sendMailExecutor)
        .execute(new MailHandler(mailProvider, EMAIL, EUserAction.RESET_PASSWORD, any(), CODE));
    MessageResponse response = verifyService.forgotPassword(EMAIL, httpServletRequest);
    String expectedCode = CommonConstant.CODE_SUCCESS;
    String actualCode = response.getCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenCheckExpiredCode_isInValid1_returnFalse() {
    when(jwtTokenService.findByToken(anyString(), any()))
        .thenReturn(TokenAuth.builder().blackList(true).build());
    Boolean flag = verifyService.checkExpiredCode(CODE);
    Assertions.assertFalse(flag);
  }

  @Test
  void whenCheckExpiredCode_isInValid2_returnFalse() {
    when(jwtTokenService.findByToken(anyString(), any()))
        .thenReturn(TokenAuth.builder().blackList(false).build());
    when(jwtProvider.validateVerifyCode(CODE)).thenReturn(false);
    Boolean flag = verifyService.checkExpiredCode(CODE);
    Assertions.assertFalse(flag);
  }

  @Test
  void whenCheckExpiredCode_isValid_returnTrue() {
    when(jwtTokenService.findByToken(anyString(), any()))
        .thenReturn(TokenAuth.builder().blackList(false).build());
    when(jwtProvider.validateVerifyCode(CODE)).thenReturn(true);
    Boolean flag = verifyService.checkExpiredCode(CODE);
    Assertions.assertTrue(flag);
  }
}
