package org.cardanofoundation.authentication.service.impl;

import com.mashape.unirest.http.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.constant.RedisConstant;
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
import org.cardanofoundation.authentication.service.AuthenticationService;
import org.cardanofoundation.authentication.thread.MailHandler;
import org.cardanofoundation.authentication.util.NonceUtils;
import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import org.json.JSONObject;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

  private final JwtProvider jwtProvider;

  private final RedisProvider redisProvider;

  private final MailProvider mailProvider;

  private final ThreadPoolExecutor sendMailExecutor;

  private final KeycloakProvider keycloakProvider;

  private final LocaleResolver localeResolver;

  @Override
  public SignInResponse signIn(SignInRequest signInRequest) {
    log.info("login is running...");
    String accountId = "";
    String password = "";
    int type = signInRequest.getType();
    if (type == 0) {
      log.info("login with email and password...");
      accountId = signInRequest.getEmail();
      password = signInRequest.getPassword();
    } else {
      log.info("login with cardano wallet...");
      accountId = signInRequest.getAddress();
      password = NonceUtils.getNonceFromSignature(signInRequest.getSignature());
    }
    AccessTokenResponse response;
    try {
      Keycloak keycloak = keycloakProvider.keycloakBuilderWhenLogin(accountId, password);
      response = keycloak.tokenManager().getAccessToken();
    } catch (Exception e) {
      log.error("Exception authentication: " + e.getMessage());
      if (type == 0) {
        throw new BusinessException(CommonErrorCode.USERNAME_OR_PASSWORD_INVALID);
      } else {
        throw new BusinessException(CommonErrorCode.SIGNATURE_INVALID);
      }
    }
    UserRepresentation user = keycloakProvider.getUser(accountId);
    UsersResource usersResource = keycloakProvider.getResource();
    Map<String, List<String>> attributes = user.getAttributes();
    if (Objects.isNull(attributes)) {
      attributes = new HashMap<>();
    }
    if (type == 1) {
      String nonce = NonceUtils.createNonce();
      attributes.put(CommonConstant.ATTRIBUTE_NONCE, List.of(nonce));
      user.setCredentials(
          Collections.singletonList(keycloakProvider.createPasswordCredentials(nonce)));
    }
    attributes.put(CommonConstant.ATTRIBUTE_LOGIN_TIME, List.of(String.valueOf(Instant.now())));
    user.setAttributes(attributes);
    usersResource.get(user.getId()).update(user);

    //add user id to token and refresh token
    redisProvider.setValue(redisProvider.getUserKeyByUserId(user.getId()),
        response.getToken());
    redisProvider.setValue(redisProvider.getUserKeyByUserId(user.getId()),
        response.getRefreshToken());

    //when user login successfully then will add user_id to each role group it contain
    List<String> roles = jwtProvider.getRolesFromJwtToken(response.getToken());
    roles.forEach(role -> {
      String roleId = keycloakProvider.getRoleIdByRoleName(role);
      redisProvider.addValueToMap(redisProvider.getRoleKeyByRoleId(roleId),user.getId(),"");
    });
    return SignInResponse.builder().token(response.getToken()).address(signInRequest.getAddress())
        .email(signInRequest.getEmail()).tokenType(CommonConstant.TOKEN_TYPE)
        .refreshToken(response.getRefreshToken()).build();
  }

  @Override
  public MessageResponse signUp(SignUpRequest signUpRequest,
      HttpServletRequest httpServletRequest) {
    Response response;
    String email = signUpRequest.getEmail();
    UserRepresentation userExist = keycloakProvider.getUser(email);
    if (Objects.nonNull(userExist) && userExist.isEnabled()) {
      throw new BusinessException(CommonErrorCode.EMAIL_IS_ALREADY_EXIST);
    }
    UsersResource usersResource = keycloakProvider.getResource();
    CredentialRepresentation encodePassword = keycloakProvider.createPasswordCredentials(
        signUpRequest.getPassword());
    if (Objects.nonNull(userExist)) {
      userExist.setCredentials(Collections.singletonList(encodePassword));
      usersResource.get(userExist.getId()).update(userExist);
      response = Response.status(Status.CREATED).build();
    } else {
      UserRepresentation newUser = new UserRepresentation();
      newUser.setUsername(email);
      newUser.setEmail(email);
      newUser.setCredentials(Collections.singletonList(encodePassword));
      newUser.setEnabled(false);
      newUser.setEmailVerified(true);
      response = usersResource.create(newUser);
    }
    if (response.getStatus() == 201) {
      String verifyCode = jwtProvider.generateCodeForVerify(email);
      sendMailExecutor.execute(
          new MailHandler(mailProvider, email, EUserAction.CREATED,
              localeResolver.resolveLocale(httpServletRequest), verifyCode));
      return MessageResponse.builder().code(CommonConstant.CODE_SUCCESS)
          .message(CommonConstant.RESPONSE_SUCCESS).build();
    }
    return new MessageResponse(CommonErrorCode.UNKNOWN_ERROR);
  }

  @Override
  public RefreshTokenResponse refreshToken(String refreshJwt,
      HttpServletRequest httpServletRequest) {
    final String accessToken = jwtProvider.parseJwt(httpServletRequest);
    if (redisProvider.isTokenBlacklisted(refreshJwt)) {
      throw new BusinessException(CommonErrorCode.REFRESH_TOKEN_EXPIRED);
    }
    try {
      JsonNode jsonNode = keycloakProvider.refreshToken(refreshJwt);
      JSONObject jsonObj = jsonNode.getObject();
      if (Objects.nonNull(jsonObj)) {
        redisProvider.blacklistJwt(accessToken, RedisConstant.JWT);
        return RefreshTokenResponse.builder().accessToken(jsonObj.get("access_token").toString())
            .refreshToken(jsonObj.get("refresh_token").toString())
            .tokenType(CommonConstant.TOKEN_TYPE).build();
      }
    } catch (Exception ex) {
      log.error(
          "Error: when generate access token from refresh token by keycloak: " + ex.getMessage());
      throw new BusinessException(CommonErrorCode.REFRESH_TOKEN_EXPIRED);
    }
    return null;
  }

  @Override
  public MessageResponse signOut(SignOutRequest signOutRequest,
      HttpServletRequest httpServletRequest) {
    String accessToken = jwtProvider.parseJwt(httpServletRequest);
    if (!redisProvider.isTokenBlacklisted(accessToken)) {
      redisProvider.blacklistJwt(accessToken, signOutRequest.getAccountId());
    }
    if (!redisProvider.isTokenBlacklisted(signOutRequest.getRefreshJwt())) {
      redisProvider.blacklistJwt(signOutRequest.getRefreshJwt(), signOutRequest.getAccountId());
    }
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public NonceResponse findNonceByAddress(String address, String walletName) {
    UserRepresentation userExist = keycloakProvider.getUser(address);
    String nonce = NonceUtils.createNonce();
    if (Objects.nonNull(userExist)) {
      userExist.setCredentials(
          Collections.singletonList(keycloakProvider.createPasswordCredentials(nonce)));
      Map<String, List<String>> attributes = userExist.getAttributes();
      attributes.put(CommonConstant.ATTRIBUTE_NONCE, List.of(nonce));
      userExist.setAttributes(attributes);
      keycloakProvider.getResource().get(userExist.getId()).update(userExist);
      return NonceResponse.builder().message(CommonConstant.CODE_SUCCESS)
          .nonce(nonce).build();
    }
    UsersResource usersResource = keycloakProvider.getResource();
    UserRepresentation newUser = new UserRepresentation();
    newUser.setUsername(address);
    newUser.setCredentials(
        Collections.singletonList(keycloakProvider.createPasswordCredentials(nonce)));
    newUser.setEnabled(true);
    newUser.setEmailVerified(true);
    Map<String, List<String>> attributes = new HashMap<>();
    attributes.put(CommonConstant.ATTRIBUTE_NONCE, List.of(nonce));
    attributes.put(CommonConstant.ATTRIBUTE_WALLET_NAME, List.of(walletName));
    newUser.setAttributes(attributes);
    usersResource.create(newUser);
    return NonceResponse.builder().message(CommonConstant.CODE_FAILURE)
        .nonce(nonce).build();
  }
}
