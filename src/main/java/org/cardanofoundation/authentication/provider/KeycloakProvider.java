package org.cardanofoundation.authentication.provider;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.config.properties.KeycloakProperties;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class KeycloakProvider {

  private final KeycloakProperties keycloakProperties;

  private Keycloak keycloak;

  public Keycloak getInstance() {
    if (Objects.isNull(keycloak)) {
      keycloak = KeycloakBuilder.builder()
          .realm(keycloakProperties.getRealm())
          .serverUrl(keycloakProperties.getAuthServerUrl())
          .clientId(keycloakProperties.getResource())
          .clientSecret(keycloakProperties.getCredentials().getSecret())
          .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
          .build();
    }
    return keycloak;
  }

  public Keycloak keycloakBuilderWhenLogin(String accountId, String password) {
    return KeycloakBuilder.builder()
        .realm(keycloakProperties.getRealm())
        .serverUrl(keycloakProperties.getAuthServerUrl())
        .clientId(keycloakProperties.getResource())
        .clientSecret(keycloakProperties.getCredentials().getSecret())
        .username(accountId)
        .password(password)
        .build();
  }

  public JsonNode refreshToken(String refreshToken) throws UnirestException {
    String url = keycloakProperties.getAuthServerUrl() + "/realms/" + keycloakProperties.getRealm()
        + "/protocol/openid-connect/token";
    return Unirest.post(url)
        .header("Content-Type", "application/x-www-form-urlencoded")
        .field("client_id", keycloakProperties.getResource())
        .field("client_secret", keycloakProperties.getCredentials().getSecret())
        .field("refresh_token", refreshToken)
        .field("grant_type", "refresh_token")
        .asJson().getBody();
  }

  public UsersResource getResource() {
    return getInstance().realm(keycloakProperties.getRealm()).users();
  }

  public UserRepresentation getUser(String accountId) {
    UsersResource usersResource = getResource();
    List<UserRepresentation> userList = usersResource.search(accountId);
    if (!userList.isEmpty()) {
      return userList.get(CommonConstant.ZERO);
    }
    return null;
  }

  public CredentialRepresentation createPasswordCredentials(String password) {
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue(password);
    return passwordCredentials;
  }
}
