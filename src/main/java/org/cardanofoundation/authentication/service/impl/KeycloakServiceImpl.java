package org.cardanofoundation.authentication.service.impl;

import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

  //REAM_ROLE: delete one role -> logout all user has this role
  //REAM_ROLE_MAPPING: assign or unassign a role of user

  //REAM_ROLE: roles-by-id/{role_id} //delete role only
  @Override
  public Boolean roleMapping(EventModel model) {
    log.info("resource type: " + model.getResourceType());// REAM_ROLE , REAM_ROLE_MAPPING
    log.info("resource path: "
        + model.getResourcePath());//(users/{user_id}) re or (users/{user_id}/role_mapping/realm) add role
    String resourceType = model.getResourceType();
    String[] resourceArr = model.getResourcePath().split("/");
    if (resourceArr.length > 0) {

      if (resourceType.equals(EResourceType.REALM_ROLE.name())
          && !resourceType.isEmpty()) {// delete role of system then remove all token of user has this role
        log.info("role id: " + resourceArr[1]);
        //get user prefix keys from role id
        Set<String> userIds = redisProvider.getAllHashKeyOfKey(
            redisProvider.getRoleKeyByRoleId(resourceArr[1]));

        Set<String> userKeys = new HashSet<>();
        userIds.forEach(
            userId -> userKeys.addAll(
                redisProvider.getKeys(redisProvider.getUserPatternKey(userId))));
        setInValidToken(userKeys);

      } else { //REAM_ROLE_MAPPING: assign or unassign a role of user
        if (!resourceType.isEmpty()) {
          log.warn("user id: {}", resourceArr[1]);
          Set<String> userKeys = redisProvider.getKeys(
              redisProvider.getUserPatternKey(resourceArr[1]));
          setInValidToken(userKeys);
        }
      }
    }
    return true;
  }

  private void setInValidToken(Set<String> keys) {
    keys.forEach(key -> {
      String val = redisProvider.getValue(key);
      if (Objects.nonNull(val)) {
        redisProvider.blacklistJwt(val, key);
        log.info("black list success: " + val);
      }
    });
  }
}
