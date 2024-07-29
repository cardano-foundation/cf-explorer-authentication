package org.cardanofoundation.authentication.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import jakarta.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import co.nstant.in.cbor.CborDecoder;
import com.bloxbean.cardano.client.address.Address;
import com.bloxbean.cardano.client.cip.cip30.CIP30DataSigner;
import com.bloxbean.cardano.client.cip.cip30.DataSignature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
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
import org.cardanofoundation.authentication.model.request.auth.SignInRequest;
import org.cardanofoundation.authentication.model.request.auth.SignOutRequest;
import org.cardanofoundation.authentication.model.request.auth.SignUpRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.auth.NonceResponse;
import org.cardanofoundation.authentication.model.response.auth.RefreshTokenResponse;
import org.cardanofoundation.authentication.model.response.auth.SignInResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.provider.MailProvider;
import org.cardanofoundation.authentication.repository.TokenAuthRepository;
import org.cardanofoundation.authentication.service.impl.AuthenticationServiceImpl;
import org.cardanofoundation.authentication.thread.MailHandler;
import org.cardanofoundation.explorer.common.exception.BusinessException;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthenticationServiceTest {

  @InjectMocks private AuthenticationServiceImpl authenticationService;

  @Mock private JwtProvider jwtProvider;

  @Mock private ThreadPoolExecutor sendMailExecutor;

  @Mock private JwtTokenService jwtTokenService;

  @Mock private TokenAuthRepository tokenAuthRepository;

  @Mock private MailProvider mailProvider;

  @Mock private KeycloakProvider keycloakProvider;

  @Mock private CborDecoder cborDecoder;

  private final String SIGNATURE_TEST =
      "84582aa201276761646472657373581de1c5499cb224c743b5341fe2d2080403329d6ec2da8892ce1a6a37ecfba166686173686564f4581e31303232323233333337313233363038333031383230373536353532363258405f2478c9c741ce481f62c2ea3369594662feda77d8b536f5713beca3f95bffa8d70d7d9268777ec42ab82f3d39254aca9ee85097677ecb7b9cf8f853618e660d";

  private final String KEY_TEST =
      "a4010103272006215820e37cc80689be14f669fba240c3e1c4ce018d353e9a4f92dbdbea2b15a18a23be";

  private final String NONCE_TEST = "102222333712360830182075655262";
  private final String ADDRESS_WALLET =
      "stake1u8z5n89jynr58df5rl3dyzqyqvef6mkzm2yf9ns6dgm7e7cjzn7df";

  private final String JWT =
      "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzb3RhdGVrIiwianRpIjoiMiIsImlhdCI6MTY3Mjk5Nzc0MSwiZXhwIjoxNjczMDg0MTQxfQ.B62gXo6iqQfHMT62q17zdhwMF8I77-P6xblKcx7ZI3-gij6YckvFYVVuoIa_qXgTTFnEeRDBQEVo3o20D1w6pffBrgbvxvMbjhOG0ONS9Xs1UQChwQs7v3lxkqoKZ8dNf0Eib43HxLZhBEBIeXa1kln4sS8osWf5iEgno0od7z9KwWK1N2Coj0o-1HE453fFyRveDJgd0DvXohbHADMmjH9t0WkXJwUK26Lv1tkqPlkIzGBPgYnYEIygdayqqt4EtP6CtgI9QOzCYSZUUFzxo-VVDzA0J7DpQbYn8G2PAuAbCXCO6lTkvmXMiyZAoZshqRhBNb7uDI66dwOJLV3NzuunSa8QOO8eNUaDoHHvR_9_J-yHTFBicoM69JHQ7UzJVyFHGmh1M8lHsJ9y6DdAobtBSyJFBhFeDj7S8bgpIvIyNoHDsf24xdlqCngE1qBsxjfp0L_yMPBxsIhW3Juopwe1c6btWTEaRaVaxhKE5yKbRsTtAzDDkdEyg_--9eXH";

  private final String REFRESH_TOKEN = "b2d4e520-4e07-43aa-9a09-f9667f52ce0e";

  private final String EMAIL = "test.30.04@gmail.com";

  private final String PASSWORD = "password";

  @Test
  void whenLoginUsingWallet_SignatureIsNull_ThrowException() {
    SignInRequest signInRequest = SignInRequest.builder().key(KEY_TEST).type(1).build();
    BusinessException exception =
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
              authenticationService.signIn(signInRequest);
            });
    String expectedCode = BusinessCode.KEY_OR_SIGNATURE_MUST_NOT_BE_NULL.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenLoginUsingWallet_KeyIsNull_ThrowException() {
    SignInRequest signInRequest = SignInRequest.builder().signature(SIGNATURE_TEST).type(1).build();
    BusinessException exception =
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
              authenticationService.signIn(signInRequest);
            });
    String expectedCode = BusinessCode.KEY_OR_SIGNATURE_MUST_NOT_BE_NULL.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenLoginUsingWallet_NonceInvalid_ThrowException() {
    String NONCE = "102222333712360830182075655262";
    SignInRequest signInRequest =
        SignInRequest.builder().signature(SIGNATURE_TEST).key(KEY_TEST).type(1).build();
    when(keycloakProvider.keycloakBuilderWhenLogin(ADDRESS_WALLET, NONCE))
        .thenReturn(
            KeycloakBuilder.builder()
                .realm("test")
                .serverUrl("http://test:9000/")
                .clientId("test")
                .clientSecret("test")
                .username(ADDRESS_WALLET)
                .password(NONCE)
                .build());
    BusinessException exception =
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
              authenticationService.signIn(signInRequest);
            });
    String expectedCode = BusinessCode.SIGNATURE_INVALID.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenLoginUsingWallet_AuthenticateSuccess_returnResponse() {
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setSignature(SIGNATURE_TEST);
    signInRequest.setKey(KEY_TEST);
    signInRequest.setType(1);
    AccessTokenResponse response = new AccessTokenResponse();
    response.setToken(JWT);
    response.setRefreshToken(REFRESH_TOKEN);
    Keycloak keycloak = Mockito.mock(Keycloak.class);
    TokenManager tokenManager = Mockito.mock(TokenManager.class);
    when(tokenManager.getAccessToken()).thenReturn(response);
    when(keycloak.tokenManager()).thenReturn(tokenManager);
    UserRepresentation userRepresentation = new UserRepresentation();
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(any())).thenReturn(userResource);
    String NONCE = "102222333712360830182075655262";
    when(keycloakProvider.keycloakBuilderWhenLogin(ADDRESS_WALLET, NONCE)).thenReturn(keycloak);
    when(keycloakProvider.getUser(ADDRESS_WALLET)).thenReturn(userRepresentation);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    doNothing().when(userResource).update(any());
    SignInResponse res = authenticationService.signIn(signInRequest);
    Assertions.assertNotNull(res);
    Assertions.assertNotNull(res.getToken());
  }

  @Test
  void whenLoginUsingEmail_EmailOrPasswordInValid_ThrowException() {
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setEmail(EMAIL);
    signInRequest.setPassword(PASSWORD);
    signInRequest.setType(0);
    BusinessException exception =
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
              authenticationService.signIn(signInRequest);
            });
    String expectedCode = BusinessCode.USERNAME_OR_PASSWORD_INVALID.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenLoginUsingEmail_AuthenticateSuccess_ReturnResponse() {
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setEmail(EMAIL);
    signInRequest.setPassword(PASSWORD);
    signInRequest.setType(0);
    AccessTokenResponse response = new AccessTokenResponse();
    response.setToken(JWT);
    response.setRefreshToken(REFRESH_TOKEN);
    Keycloak keycloak = Mockito.mock(Keycloak.class);
    TokenManager tokenManager = Mockito.mock(TokenManager.class);
    when(tokenManager.getAccessToken()).thenReturn(response);
    when(keycloak.tokenManager()).thenReturn(tokenManager);
    UserRepresentation userRepresentation = new UserRepresentation();
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(any())).thenReturn(userResource);
    when(keycloakProvider.keycloakBuilderWhenLogin(EMAIL, PASSWORD)).thenReturn(keycloak);
    when(keycloakProvider.getUser(EMAIL)).thenReturn(userRepresentation);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    doNothing().when(userResource).update(any());
    SignInResponse res = authenticationService.signIn(signInRequest);
    Assertions.assertNotNull(res);
    Assertions.assertNotNull(res.getToken());
  }

  @Test
  void whenSignUp_EmailIsExist_ThrowException() {
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setEmail(EMAIL);
    signUpRequest.setPassword(PASSWORD);
    UserRepresentation user = new UserRepresentation();
    user.setEnabled(true);
    when(keycloakProvider.getUser(EMAIL)).thenReturn(user);
    BusinessException exception =
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
              authenticationService.signUp(signUpRequest, httpServletRequest);
            });
    String expectedCode = BusinessCode.EMAIL_IS_ALREADY_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  @Disabled
  void whenSignUp_EmailIsNotExist_returnResponseSuccess() {
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setEmail(EMAIL);
    signUpRequest.setPassword(PASSWORD);
    when(keycloakProvider.getUser(EMAIL)).thenReturn(null);
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue(PASSWORD);
    when(keycloakProvider.createPasswordCredentials(PASSWORD)).thenReturn(passwordCredentials);
    UserRepresentation user = new UserRepresentation();
    user.setUsername(EMAIL);
    user.setEmail(EMAIL);
    user.setCredentials(Collections.singletonList(passwordCredentials));
    user.setEnabled(false);
    user.setEmailVerified(true);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    when(usersResource.create(any())).thenReturn(Response.status(Status.CREATED).build());
    when(jwtProvider.generateCodeForVerify(EMAIL)).thenReturn(JWT);
    doNothing()
        .when(sendMailExecutor)
        .execute(new MailHandler(mailProvider, EMAIL, EUserAction.CREATED, any(), JWT));
    MessageResponse response = authenticationService.signUp(signUpRequest, httpServletRequest);
    String expectedCode = CommonConstant.CODE_SUCCESS;
    Assertions.assertEquals(expectedCode, response.getCode());
  }

  @Test
  @Disabled
  void whenSignUp_EmailIsExistWithStatusDisabled_returnResponseSuccess() {
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setEmail(EMAIL);
    signUpRequest.setPassword(PASSWORD);
    UserRepresentation user = new UserRepresentation();
    user.setEnabled(false);
    when(keycloakProvider.getUser(EMAIL)).thenReturn(user);
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue(PASSWORD);
    when(keycloakProvider.createPasswordCredentials(PASSWORD)).thenReturn(passwordCredentials);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    when(usersResource.get(any())).thenReturn(userResource);
    doNothing().when(userResource).update(any());
    when(jwtProvider.generateCodeForVerify(EMAIL)).thenReturn(JWT);
    doNothing()
        .when(sendMailExecutor)
        .execute(new MailHandler(mailProvider, EMAIL, EUserAction.CREATED, any(), JWT));
    MessageResponse response = authenticationService.signUp(signUpRequest, httpServletRequest);
    String expectedCode = CommonConstant.CODE_SUCCESS;
    Assertions.assertEquals(expectedCode, response.getCode());
  }

  @Test
  void whenRefreshToken_RefreshTokenIsInValid_ThrowException() {
    when(jwtTokenService.isBlacklistToken(any(), any())).thenReturn(true);
    when(jwtProvider.parseJwt(any())).thenReturn(JWT);
    BusinessException exception =
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
              authenticationService.refreshToken(REFRESH_TOKEN, any());
            });
    String expectedCode = BusinessCode.REFRESH_TOKEN_EXPIRED.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenRefreshToken_RefreshTokenIsValid_returnResponse()
      throws UnirestException, JSONException {
    when(jwtProvider.parseJwt(any())).thenReturn(JWT);
    JsonNode jsonNode = Mockito.mock(JsonNode.class);
    JSONObject jsonObj = Mockito.mock(JSONObject.class);
    when(keycloakProvider.refreshToken(REFRESH_TOKEN)).thenReturn(jsonNode);
    when(jwtTokenService.isBlacklistToken(any(), any())).thenReturn(false);
    when(jsonNode.getObject()).thenReturn(jsonObj);
    when(jsonObj.get(any())).thenReturn(JWT);
    RefreshTokenResponse response = authenticationService.refreshToken(REFRESH_TOKEN, any());
    Assertions.assertEquals(JWT, response.getAccessToken());
  }

  @Test
  void whenSignOut_returnResponseSuccess() {
    SignOutRequest request = new SignOutRequest();
    request.setRefreshJwt(REFRESH_TOKEN);
    request.setAccountId(EMAIL);
    when(jwtProvider.parseJwt(any())).thenReturn(JWT);
    MessageResponse response = authenticationService.signOut(request, any());
    String expectedCode = CommonConstant.CODE_SUCCESS;
    Assertions.assertEquals(expectedCode, response.getCode());
  }

  @Test
  void whenFindNonce_WalletIsNotExist_returnResponse() {
    when(keycloakProvider.getUser(ADDRESS_WALLET)).thenReturn(null);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue(PASSWORD);
    when(keycloakProvider.createPasswordCredentials(any())).thenReturn(passwordCredentials);
    Response response = Mockito.mock(Response.class);
    when(usersResource.create(any())).thenReturn(response);
    NonceResponse res = authenticationService.findNonceByAddress(ADDRESS_WALLET, "NAMI");
    String expectedCode = CommonConstant.CODE_FAILURE;
    Assertions.assertEquals(expectedCode, res.getMessage());
  }

  @Test
  void whenFindNonce_WalletIsExist_returnResponse() {
    UserRepresentation user = Mockito.mock(UserRepresentation.class);
    when(user.getAttributes()).thenReturn(new HashMap<>());
    when(keycloakProvider.getUser(ADDRESS_WALLET)).thenReturn(user);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(any())).thenReturn(userResource);
    doNothing().when(userResource).update(any());
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue(PASSWORD);
    when(keycloakProvider.createPasswordCredentials(any())).thenReturn(passwordCredentials);
    Response response = Mockito.mock(Response.class);
    when(usersResource.create(any())).thenReturn(response);
    NonceResponse res = authenticationService.findNonceByAddress(ADDRESS_WALLET, "NAMI");
    String expectedCode = CommonConstant.CODE_SUCCESS;
    Assertions.assertEquals(expectedCode, res.getMessage());
  }

  @Test
  void verifySignature() throws JsonProcessingException {
    Map<String, String> data = new HashMap<>();
    data.put("signature", SIGNATURE_TEST);
    data.put("key", KEY_TEST);
    ObjectMapper objectMapper = new ObjectMapper();
    String jacksonData = objectMapper.writeValueAsString(data);
    DataSignature from = DataSignature.from(jacksonData);
    DataSignature dataSignature = new DataSignature(from.signature(), from.key());
    Address address = new Address(dataSignature.address());
    String addressString = address.toBech32();
    // verify
    boolean verified = CIP30DataSigner.INSTANCE.verify(dataSignature);
    String nonce = new String(dataSignature.coseSign1().payload());
    Assertions.assertEquals(NONCE_TEST, nonce);
    Assertions.assertEquals(ADDRESS_WALLET, addressString);
    Assertions.assertTrue(verified);
  }

  @Test
  void verifySignature_nonceIsInvalid() throws JsonProcessingException {
    Map<String, String> data = new HashMap<>();
    data.put("signature", SIGNATURE_TEST);
    data.put("key", KEY_TEST);
    ObjectMapper objectMapper = new ObjectMapper();
    String jacksonData = objectMapper.writeValueAsString(data);
    DataSignature from = DataSignature.from(jacksonData);
    DataSignature dataSignature = new DataSignature(from.signature(), from.key());
    Address address = new Address(dataSignature.address());
    String addressString = address.toBech32();
    // verify
    boolean verified = CIP30DataSigner.INSTANCE.verify(dataSignature);
    String nonce = new String(dataSignature.coseSign1().payload());
    Assertions.assertNotEquals("NONCE_TEST", nonce);
    Assertions.assertNotEquals("ADDRESS_WALLET", addressString);
    Assertions.assertTrue(verified);
  }
}
