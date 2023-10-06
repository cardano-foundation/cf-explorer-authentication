package org.cardanofoundation.authentication.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.enums.EResourceType;
import org.cardanofoundation.authentication.model.request.event.EventModel;
import org.cardanofoundation.authentication.model.response.UserInfoResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.provider.RedisProvider;
import org.cardanofoundation.authentication.service.KeycloakService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class KeycloakServiceImpl implements KeycloakService {

  private final KeycloakProvider keycloakProvider;

  private final JwtProvider jwtProvider;

  private final RedisProvider redisProvider;

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

  @Override
  public Boolean roleMapping(EventModel model) {
    log.info("resource type: " + model.getResourceType());
    log.info("resource path: " + model.getResourcePath());
    String resourceType = model.getResourceType();
    String[] resourceArr = model.getResourcePath().split("/");
    Set<String> keys = redisProvider.getKeys(resourceArr[1] + "*");
    if (Objects.nonNull(keys) && !keys.isEmpty()) {
      if (resourceType.equals(EResourceType.REALM_ROLE.name())) {
        log.info("role id: " + resourceArr[1]);
        Set<String> userPrefixKeys = new HashSet<>();
        keys.forEach(key -> userPrefixKeys.add(redisProvider.getValue(key)));
        Set<String> userKeys = new HashSet<>();
        userPrefixKeys.forEach(
            userPrefixKey -> userKeys.addAll(redisProvider.getKeys(userPrefixKey + "*")));
        setInValidToken(userKeys);
      } else {
        log.info("user id: " + resourceArr[1] );
        setInValidToken(keys);
      }
    }
    return true;
  }

  private void setInValidToken(Set<String> keys) {
    keys.forEach(key -> {
      String val = redisProvider.getValue(key);
      redisProvider.blacklistJwt(val, key);
      redisProvider.remove(key);
      log.info("black list: " + key);
    });
  }
}
