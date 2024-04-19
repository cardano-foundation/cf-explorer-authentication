package org.cardanofoundation.authentication.provider;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import org.cardanofoundation.authentication.constant.RedisConstant;
import org.cardanofoundation.explorer.common.exception.BusinessException;
import org.cardanofoundation.explorer.common.exception.CommonErrorCode;
import org.cardanofoundation.explorer.common.utils.StringUtils;

@Component
@Log4j2
@RequiredArgsConstructor
public class RedisProvider {

  @Value("${timeToLiveRedisSignOut}")
  private int timeToLiveRedisSignOut;

  private final RedisTemplate<String, String> redisTemplate;

  private static final String USER_PREFIX = "USER_";

  private static final String ROLE_PREFIX = "ROLE_";

  /*
   * @since: 06/12/2022
   * description: Add access token + accountId to redis after logout or refresh token
   * @update:
   */
  public void blacklistJwt(String token, String accountId) {
    log.info("Blacklist token: " + token + " with accountId: " + accountId);
    if (!isTokenBlacklisted(token)) {
      redisTemplate
          .opsForValue()
          .set(RedisConstant.JWT + token, accountId, timeToLiveRedisSignOut, TimeUnit.HOURS);
    }
  }

  /*
   * @since: 06/12/2022
   * description: Check exist access token + accountId from redis
   * @update:
   */
  public boolean isTokenBlacklisted(String token) {
    if (Boolean.TRUE.equals(StringUtils.isNullOrEmpty(token))) {
      throw new BusinessException(CommonErrorCode.INVALID_TOKEN);
    }
    return Boolean.TRUE.equals(redisTemplate.hasKey(RedisConstant.JWT + token));
  }

  /*
   * @since: 25/09/2023
   * description: get all key using prefix key
   * @update:
   */
  public Set<String> getKeys(String pattern) {
    return redisTemplate.keys(pattern);
  }

  public String getUserPatternKey(String userId) {
    return USER_PREFIX + userId + "*";
  }

  /*
   * @since: 25/09/2023
   * description: get value from key
   * @update:
   */
  public String getValue(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public List<String> getValues(Set<String> keys) {
    return redisTemplate.opsForValue().multiGet(keys);
  }

  /*
   * @since: 25/09/2023
   * description: set key + value redis
   * @update:
   */
  public void setValue(String key, String val) {
    redisTemplate.opsForValue().set(key, val, timeToLiveRedisSignOut, TimeUnit.HOURS);
  }

  public void addValueToMap(String key, String hashKey, String value) {
    redisTemplate.opsForHash().put(key, hashKey, value);
  }

  public Set<String> getAllHashKeyOfKey(String keys) {
    return redisTemplate.opsForHash().keys(keys).stream()
        .map(Object::toString)
        .collect(Collectors.toSet());
  }

  /*
   * @since: 25/09/2023
   * description: delete key + value redis
   * @update:
   */
  public void remove(String key) {
    redisTemplate.delete(key);
  }

  public String getRoleKeyByRoleId(String roleId) {
    return ROLE_PREFIX + roleId;
  }

  public String getUserKeyByUserId(String userId) {
    return USER_PREFIX + userId + "_" + UUID.randomUUID();
  }
}
