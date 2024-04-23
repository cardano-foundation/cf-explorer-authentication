package org.cardanofoundation.authentication.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import jakarta.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.ByteString;
import co.nstant.in.cbor.model.DataItem;
import com.bloxbean.cardano.client.cip.cip8.SigStructure;
import com.bloxbean.cardano.client.util.HexUtil;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.keycloak.admin.client.Keycloak;
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
import org.cardanofoundation.authentication.constant.RedisConstant;
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
import org.cardanofoundation.authentication.provider.RedisProvider;
import org.cardanofoundation.authentication.service.impl.AuthenticationServiceImpl;
import org.cardanofoundation.authentication.thread.MailHandler;
import org.cardanofoundation.explorer.common.exception.BusinessException;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthenticationServiceTest {

  @InjectMocks private AuthenticationServiceImpl authenticationService;

  @Mock private JwtProvider jwtProvider;

  @Mock private RedisProvider redisProvider;

  @Mock private ThreadPoolExecutor sendMailExecutor;

  @Mock private MailProvider mailProvider;

  @Mock private KeycloakProvider keycloakProvider;

  @Mock private CborDecoder cborDecoder;

  private final String SIGNATURE_TEST =
      "84582aa201276761646472657373581de18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909a166686173686564f453393534353735313438313233323636333232355840850ff657e23963414e7c1bf708928dc994ecafea29790089c810af1ac7486aae12a4ed736d16528051aeff1991ee8d2aef19fe3d375f3ad019925ff1530ed608";

  private final String ADDRESS_WALLET =
      "stake1u80n7nvm3qlss9ls0krp5xh7sqxlazp8kz6n3fp5sgnul5cnxyg4p";

  private final String JWT =
      "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzb3RhdGVrIiwianRpIjoiMiIsImlhdCI6MTY3Mjk5Nzc0MSwiZXhwIjoxNjczMDg0MTQxfQ.B62gXo6iqQfHMT62q17zdhwMF8I77-P6xblKcx7ZI3-gij6YckvFYVVuoIa_qXgTTFnEeRDBQEVo3o20D1w6pffBrgbvxvMbjhOG0ONS9Xs1UQChwQs7v3lxkqoKZ8dNf0Eib43HxLZhBEBIeXa1kln4sS8osWf5iEgno0od7z9KwWK1N2Coj0o-1HE453fFyRveDJgd0DvXohbHADMmjH9t0WkXJwUK26Lv1tkqPlkIzGBPgYnYEIygdayqqt4EtP6CtgI9QOzCYSZUUFzxo-VVDzA0J7DpQbYn8G2PAuAbCXCO6lTkvmXMiyZAoZshqRhBNb7uDI66dwOJLV3NzuunSa8QOO8eNUaDoHHvR_9_J-yHTFBicoM69JHQ7UzJVyFHGmh1M8lHsJ9y6DdAobtBSyJFBhFeDj7S8bgpIvIyNoHDsf24xdlqCngE1qBsxjfp0L_yMPBxsIhW3Juopwe1c6btWTEaRaVaxhKE5yKbRsTtAzDDkdEyg_--9eXH";

  private final String REFRESH_TOKEN = "b2d4e520-4e07-43aa-9a09-f9667f52ce0e";

  private final String EMAIL = "test.30.04@gmail.com";

  private final String PASSWORD = "password";

  @Test
  void whenLoginUsingWallet_SignatureInValid_ThrowException() {
    String signatureInValid = "Test123456789";
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setSignature(signatureInValid);
    signInRequest.setType(1);
    Exception exception =
        Assertions.assertThrows(
            Exception.class,
            () -> {
              authenticationService.signIn(signInRequest);
            });
    String expectedMessage = "Invalid hexadecimal";
    String actualMessage = exception.getMessage();
    Assertions.assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void whenLoginUsingWallet_AuthenticateFail_ThrowException() {
    String addressWallet = "Test123456789";
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setSignature(SIGNATURE_TEST);
    signInRequest.setAddress(addressWallet);
    signInRequest.setType(1);
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
    signInRequest.setAddress(ADDRESS_WALLET);
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
    String NONCE = "9545751481232663225";
    when(keycloakProvider.keycloakBuilderWhenLogin(ADDRESS_WALLET, NONCE)).thenReturn(keycloak);
    when(keycloakProvider.getUser(ADDRESS_WALLET)).thenReturn(userRepresentation);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    doNothing().when(userResource).update(any());
    SignInResponse res = authenticationService.signIn(signInRequest);
    Assertions.assertNotNull(res);
    Assertions.assertNotNull(res.getToken());
  }

  @Test
  void test() throws CborException {
    String signature =
        "84582aa201276761646472657373581de16bc80fd23cf1be6f62185443a9cfa26454d793e704fe581a97ba670ca166686173686564f4581b373434333837303133393338393139373430323432383832353736584041be4aee6327705358a1a7c19598eac26e82eb90891383c02eb968a46756d6f17d7be6f03b7c7f79dfc734c7450abb47901a2674cb1133c1ec948ab2185d5200";

    String address = "stake1u94usr7j8ncmummzrp2y82w05fj9f4unuuz0ukq6j7axwrqv9uf0w";

    String nonce = "744387013938919740242882576";

    List<DataItem> itemList = null;

    itemList = CborDecoder.decode(HexUtil.decodeHexString(signature));

    List<DataItem> topArray = ((Array) itemList.get(0)).getDataItems();

    ByteString messageToSign = (ByteString) topArray.get(2);
    byte[] message = messageToSign.getBytes();
    System.out.println(new String(message));

    SigStructure sigStructure = SigStructure.deserialize(topArray.get(3));

    System.out.println(Arrays.toString(sigStructure.payload()));

    //    System.out.printf("Adddress: %s\n", Hex.encodeHexString(address.getBytes()));
    //
    //
    //    COSESign1 coseSign1 = COSESign1.deserialize(HexUtil.decodeHexString(signature));
    //
    //    System.out.println(Hex.encodeHexString(coseSign1.headers()._protected().getBytes()));

    //    List<DataItem> itemList = null;
    //    try {
    //      itemList = CborDecoder.decode(HexUtil.decodeHexString(signature));
    //    } catch (CborException e) {
    //      throw new BusinessException(CommonErrorCode.UNKNOWN_ERROR);
    //    }
    //    List<DataItem> topArray = ((Array) itemList.get(0)).getDataItems();
    //    ByteString messageToSign = (ByteString) topArray.get(2);
    //    byte[] message = messageToSign.getBytes();
    //    System.out.println(new String(message));
    //    List<DataItem> itemList = null;
    //    itemList = CborDecoder.decode(HexUtil.decodeHexString(signature));
    //    List<DataItem> topArray = ((Array) itemList.get(0)).getDataItems();

    //    List<COSERecipient> recipients = messageToSign.getDataItems().stream()
    //        .map(dataItem -> COSERecipient.deserialize((Array) dataItem))
    //        .toList();

    //    COSERecipient recipient = COSERecipient.deserialize((Array) topArray.get(3));
    //
    //    System.out.println(HexUtil.encodeHexString(recipient.ciphertext()));

    //    itemList = CborDecoder.decode(message);
    //
    //    List<DataItem> array = ((Array) itemList.get(0)).getDataItems();
    //    ByteString item = (ByteString) topArray.get(2);
    //
    //    byte[] message1 = item.getBytes();
    //
    //    System.out.println(Hex.encodeHexString(message1));

    //    Headers headers = Headers.deserialize(new DataItem[]{topArray.get(0), topArray.get(1)});
    //    Array cosRecptArray = new Array();
    //    Arrays.stream(headers.serialize())
    //        .forEach(header -> cosRecptArray.add(header));
    //
    //    cosRecptArray.add(new ByteString(address.getBytes()));
    //
    //    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //    new CborEncoder(baos).encode(cosRecptArray);
    //    byte[] bytes = baos.toByteArray();
    //
    //    System.out.println(HexUtil.encodeHexString(bytes));

    //    ByteString header = (ByteString) topArray.get(0);
    //    byte[] headerMessage = header.getBytes();
    //
    //    System.out.printf("Header: %s\n", Hex.encodeHexString(headerMessage));
    //
    //    header = (ByteString) topArray.get(1);
    //    headerMessage = header.getBytes();
    //
    //    System.out.printf("Header: %s\n", Hex.encodeHexString(headerMessage));

    //    header = (ByteString) topArray.get(2);
    //    headerMessage = header.getBytes();
    //
    //    System.out.printf("Header: %s\n", Hex.encodeHexString(headerMessage));

    //    Array recipientArray = ((Array) topArray.get(3));

    //    List<COSERecipient> recipients = recipientArray.getDataItems().stream()
    //        .map(dataItem -> {
    //          byte[] ciphertext = ((ByteString) dataItem.get(2)).getBytes();
    //        })
    //        .toList();
    //    System.out.println(recipients.get(0));

    //    System.out.println(Hex.encodeHexString(message));
    //    System.out.println(topArray.get(3));
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
    when(jwtProvider.parseJwt(any())).thenReturn(JWT);
    when(redisProvider.isTokenBlacklisted(REFRESH_TOKEN)).thenReturn(true);
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
  void whenRefreshToken_RefreshTokenIsValid_returnResponse() throws UnirestException {
    when(jwtProvider.parseJwt(any())).thenReturn(JWT);
    when(redisProvider.isTokenBlacklisted(REFRESH_TOKEN)).thenReturn(false);
    JsonNode jsonNode = Mockito.mock(JsonNode.class);
    JSONObject jsonObj = Mockito.mock(JSONObject.class);
    when(keycloakProvider.refreshToken(REFRESH_TOKEN)).thenReturn(jsonNode);
    when(jsonNode.getObject()).thenReturn(jsonObj);
    doNothing().when(redisProvider).blacklistJwt(JWT, RedisConstant.JWT);
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
    when(redisProvider.isTokenBlacklisted(JWT)).thenReturn(false);
    when(redisProvider.isTokenBlacklisted(REFRESH_TOKEN)).thenReturn(false);
    doNothing().when(redisProvider).blacklistJwt(JWT, EMAIL);
    doNothing().when(redisProvider).blacklistJwt(REFRESH_TOKEN, EMAIL);
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
}
