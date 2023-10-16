package org.cardanofoundation.authentication.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.response.UserInfoResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.service.impl.KeycloakServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class KeycloakServiceTest {

  @InjectMocks
  private KeycloakServiceImpl keycloakService;

  @Mock
  private KeycloakProvider keycloakProvider;

  @Mock
  private JwtProvider jwtProvider;

  private final String EMAIL = "test@gmail.com";

  @Test
  void whenCheckExistEmail_isExist_returnTrue() {
    UserRepresentation user = new UserRepresentation();
    when(keycloakProvider.getUser(EMAIL)).thenReturn(user);
    Boolean flag = keycloakService.checkExistEmail(EMAIL);
    Assertions.assertTrue(flag);
  }

  @Test
  void whenCheckExistEmail_isNotExist_returnFalse() {
    when(keycloakProvider.getUser(EMAIL)).thenReturn(null);
    Boolean flag = keycloakService.checkExistEmail(EMAIL);
    Assertions.assertFalse(flag);
  }

  @Test
  void whenUserInfo_returnResponse() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(EMAIL);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(EMAIL)).thenReturn(userResource);
    UserRepresentation user = Mockito.mock(UserRepresentation.class);
    when(user.getUsername()).thenReturn(EMAIL);
    when(user.firstAttribute(CommonConstant.ATTRIBUTE_LOGIN_TIME)).thenReturn(
        "2023-09-21T09:42:15.191104040Z");
    when(userResource.toRepresentation()).thenReturn(user);
    UserInfoResponse response = keycloakService.infoUser(httpServletRequest);
    Assertions.assertEquals(EMAIL, response.getUsername());
    Assertions.assertEquals("2023-09-21T09:42:15.191104040Z", response.getLastLogin().toString());
  }
}