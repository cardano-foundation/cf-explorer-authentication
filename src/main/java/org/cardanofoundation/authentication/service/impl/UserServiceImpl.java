package org.cardanofoundation.authentication.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import org.keycloak.representations.idm.UserRepresentation;

import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.service.UserService;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

  private final JwtProvider jwtProvider;

  private final KeycloakProvider keycloakProvider;

  @Override
  public Boolean setTimezoneForUser(String timezone, HttpServletRequest httpServletRequest) {
    try {
      if (Objects.nonNull(timezone) && !timezone.isBlank()) {
        timezone = timezone.trim();
      } else {
        timezone = CommonConstant.DEFAULT_TIMEZONE;
      }
      String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
      UserRepresentation user = keycloakProvider.getResource().get(accountId).toRepresentation();
      Map<String, List<String>> attributes = user.getAttributes();
      attributes.put(CommonConstant.TIMEZONE_KEY, List.of(timezone));
      user.setAttributes(attributes);
      keycloakProvider.getResource().get(user.getId()).update(user);
    } catch (Exception e) {
      log.error("Error when set timezone for user: {}", e.getMessage());
      return false;
    }
    return true;
  }
}
