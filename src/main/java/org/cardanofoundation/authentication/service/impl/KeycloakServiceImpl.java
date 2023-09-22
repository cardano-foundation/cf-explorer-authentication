package org.cardanofoundation.authentication.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.response.UserInfoResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.service.KeycloakService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class KeycloakServiceImpl implements KeycloakService {

  private final KeycloakProvider keycloakProvider;

  private final JwtProvider jwtProvider;

  @Override
  public Boolean checkExistEmail(String email) {
    UserRepresentation user = keycloakProvider.getUser(email);
    return !Objects.isNull(user);
  }

  @Override
  public UserInfoResponse infoUser(HttpServletRequest httpServletRequest) {
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserRepresentation user = keycloakProvider.getResource().get(accountId).toRepresentation();
    return UserInfoResponse.builder().username(user.getUsername())
        .lastLogin(Instant.parse(user.firstAttribute(CommonConstant.ATTRIBUTE_LOGIN_TIME))).build();
  }
}
