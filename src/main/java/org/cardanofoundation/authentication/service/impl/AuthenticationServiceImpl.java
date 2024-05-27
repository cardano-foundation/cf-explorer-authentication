package org.cardanofoundation.authentication.service.impl;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import com.bloxbean.cardano.client.address.Address;
import com.bloxbean.cardano.client.cip.cip30.CIP30DataSigner;
import com.bloxbean.cardano.client.cip.cip30.DataSignature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.JsonNode;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONObject;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

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
import org.cardanofoundation.authentication.service.AuthenticationService;
import org.cardanofoundation.authentication.service.JwtTokenService;
import org.cardanofoundation.authentication.thread.MailHandler;
import org.cardanofoundation.authentication.util.NonceUtils;
import org.cardanofoundation.explorer.common.entity.enumeration.TokenAuthType;
import org.cardanofoundation.explorer.common.entity.explorer.TokenAuth;
import org.cardanofoundation.explorer.common.exception.BusinessException;
import org.cardanofoundation.explorer.common.exception.CommonErrorCode;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

  private final JwtProvider jwtProvider;

  private final MailProvider mailProvider;

  private final ThreadPoolExecutor sendMailExecutor;

  private final KeycloakProvider keycloakProvider;

  private final LocaleResolver localeResolver;

  private final JwtTokenService jwtTokenService;

  @Override
  public SignInResponse signIn(SignInRequest signInRequest) {
    String accountId = "";
    String password = "";
    int type = signInRequest.getType();
    if (type == 0) {
      log.info("login with email and password...");
      accountId = signInRequest.getEmail();
      password = signInRequest.getPassword();
    } else {
      log.info("login with cardano wallet...");
      UsernamePasswordCredentials userPass =
          verifySignatureThenGetUserPassCredential(signInRequest);
      accountId = userPass.getUserName();
      password = userPass.getPassword();
    }
    AccessTokenResponse response;
    try {
      Keycloak keycloak = keycloakProvider.keycloakBuilderWhenLogin(accountId, password);
      response = keycloak.tokenManager().getAccessToken();
    } catch (Exception e) {
      log.error("Exception authentication: " + e.getMessage());
      if (type == 0) {
        throw new BusinessException(BusinessCode.USERNAME_OR_PASSWORD_INVALID);
      } else {
        throw new BusinessException(BusinessCode.SIGNATURE_INVALID);
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

    String timezone =
        attributes.containsKey(CommonConstant.TIMEZONE_KEY)
            ? attributes.get(CommonConstant.TIMEZONE_KEY).get(0)
            : CommonConstant.DEFAULT_TIMEZONE;

    usersResource.get(user.getId()).update(user);

    TokenAuth accessToken =
        new TokenAuth(response.getToken(), user.getId(), TokenAuthType.ACCESS_TOKEN);
    TokenAuth refreshToken =
        new TokenAuth(response.getRefreshToken(), user.getId(), TokenAuthType.REFRESH_TOKEN);
    jwtTokenService.saveToken(List.of(accessToken, refreshToken));
    // when user login successfully then will add user_id to each role group it contain
    List<String> roles = jwtProvider.getRolesFromJwtToken(response.getToken());
    roles.forEach(
        role -> {
          String roleId = keycloakProvider.getRoleIdByRoleName(role);
          jwtTokenService.saveUserRoleMapping(user.getId(), roleId);
        });
    return SignInResponse.builder()
        .token(response.getToken())
        .address(type == 0 ? null : accountId)
        .email(signInRequest.getEmail())
        .tokenType(CommonConstant.TOKEN_TYPE)
        .refreshToken(response.getRefreshToken())
        .timezone(timezone)
        .build();
  }

  @Override
  @Transactional
  public MessageResponse signUp(
      SignUpRequest signUpRequest, HttpServletRequest httpServletRequest) {
    Response response;
    String userId = null;
    String email = signUpRequest.getEmail();
    UserRepresentation userExist = keycloakProvider.getUser(email);
    if (Objects.nonNull(userExist) && userExist.isEnabled()) {
      throw new BusinessException(BusinessCode.EMAIL_IS_ALREADY_EXIST);
    }
    UsersResource usersResource = keycloakProvider.getResource();
    CredentialRepresentation encodePassword =
        keycloakProvider.createPasswordCredentials(signUpRequest.getPassword());
    if (Objects.nonNull(userExist)) {
      userExist.setCredentials(Collections.singletonList(encodePassword));
      usersResource.get(userExist.getId()).update(userExist);
      userId = userExist.getId();
      response = Response.status(Status.CREATED).build();
    } else {
      UserRepresentation newUser = new UserRepresentation();
      newUser.setUsername(email);
      newUser.setEmail(email);
      newUser.setCredentials(Collections.singletonList(encodePassword));
      newUser.setEnabled(false);
      newUser.setEmailVerified(true);
      userId = newUser.getId();
      response = usersResource.create(newUser);
    }
    log.info("sign up email: " + email);
    if (response.getStatus() == 201) {
      String verifyCode = jwtProvider.generateCodeForVerify(email);
      TokenAuth verifyToken = new TokenAuth(verifyCode, userId, TokenAuthType.VERIFY_CODE);
      jwtTokenService.saveToken(List.of(verifyToken));
      sendMailExecutor.execute(
          new MailHandler(
              mailProvider,
              email,
              EUserAction.CREATED,
              localeResolver.resolveLocale(httpServletRequest),
              verifyCode));
      return MessageResponse.builder()
          .code(CommonConstant.CODE_SUCCESS)
          .message(CommonConstant.RESPONSE_SUCCESS)
          .build();
    }
    return new MessageResponse(CommonErrorCode.UNKNOWN_ERROR);
  }

  @Override
  public RefreshTokenResponse refreshToken(
      String refreshJwt, HttpServletRequest httpServletRequest) {
    final String accessToken = jwtProvider.parseJwt(httpServletRequest);
    TokenAuth tokenAuth = jwtTokenService.findByToken(refreshJwt, TokenAuthType.REFRESH_TOKEN);
    if (tokenAuth.getBlackList()) {
      throw new BusinessException(BusinessCode.REFRESH_TOKEN_EXPIRED);
    }
    try {
      JsonNode jsonNode = keycloakProvider.refreshToken(refreshJwt);
      JSONObject jsonObj = jsonNode.getObject();
      if (Objects.nonNull(jsonObj)) {
        jwtTokenService.blacklistToken(accessToken, TokenAuthType.ACCESS_TOKEN);
        String newAccessToken = jsonObj.get("access_token").toString();
        TokenAuth newTokenAuth =
            new TokenAuth(newAccessToken, tokenAuth.getUserId(), TokenAuthType.ACCESS_TOKEN);
        jwtTokenService.saveToken(List.of(newTokenAuth));
        return RefreshTokenResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(jsonObj.get("refresh_token").toString())
            .tokenType(CommonConstant.TOKEN_TYPE)
            .build();
      }
    } catch (Exception ex) {
      log.error(
          "Error: when generate access token from refresh token by keycloak: " + ex.getMessage());
      throw new BusinessException(BusinessCode.REFRESH_TOKEN_EXPIRED);
    }
    return null;
  }

  @Override
  public MessageResponse signOut(
      SignOutRequest signOutRequest, HttpServletRequest httpServletRequest) {
    String accessToken = jwtProvider.parseJwt(httpServletRequest);
    jwtTokenService.blacklistToken(accessToken, TokenAuthType.ACCESS_TOKEN);
    jwtTokenService.blacklistToken(signOutRequest.getRefreshJwt(), TokenAuthType.REFRESH_TOKEN);
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public NonceResponse findNonceByAddress(String address, String walletName) {
    log.info("Wallet {}: Get nonce by address: {}", walletName, address);
    UserRepresentation userExist = keycloakProvider.getUser(address);
    String nonce = NonceUtils.createNonce();
    if (Objects.nonNull(userExist)) {
      userExist.setCredentials(
          Collections.singletonList(keycloakProvider.createPasswordCredentials(nonce)));
      Map<String, List<String>> attributes = userExist.getAttributes();
      attributes.put(CommonConstant.ATTRIBUTE_NONCE, List.of(nonce));
      userExist.setAttributes(attributes);
      keycloakProvider.getResource().get(userExist.getId()).update(userExist);
      return NonceResponse.builder().message(CommonConstant.CODE_SUCCESS).nonce(nonce).build();
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
    return NonceResponse.builder().message(CommonConstant.CODE_FAILURE).nonce(nonce).build();
  }

  @Override
  public UsernamePasswordCredentials verifySignatureThenGetUserPassCredential(
      SignInRequest signInRequest) {
    Map<String, String> data = new HashMap<>();
    if (Objects.isNull(signInRequest.getSignature()) || Objects.isNull(signInRequest.getKey())) {
      throw new BusinessException(BusinessCode.KEY_OR_SIGNATURE_MUST_NOT_BE_NULL);
    }
    data.put("signature", signInRequest.getSignature());
    data.put("key", signInRequest.getKey());
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      String jacksonData = objectMapper.writeValueAsString(data);
      DataSignature from = DataSignature.from(jacksonData);
      DataSignature dataSignature = new DataSignature(from.signature(), from.key());
      Address address = new Address(dataSignature.address());
      String addressString = address.toBech32();
      // verify
      boolean verified = CIP30DataSigner.INSTANCE.verify(dataSignature);
      if (!verified) {
        throw new BusinessException(BusinessCode.SIGNATURE_NOT_VERIFIED);
      }
      String nonce = new String(dataSignature.coseSign1().payload());
      return new UsernamePasswordCredentials(addressString, nonce);
    } catch (JsonProcessingException e) {
      log.error("Error when parsing to object: " + e.getMessage());
    } catch (Exception e) {
      log.error("Error when verified signature: " + e.getMessage());
    }
    return null;
  }
}
