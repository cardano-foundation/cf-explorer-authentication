package org.cardanofoundation.authentication.provider;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.constant.RedisConstant;
import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import org.cardanofoundation.explorer.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class RedisProvider {

  @Value("${timeToLiveRedisSignOut}")
  private int timeToLiveRedisSignOut;

  private final RedisTemplate<String, Object> redisTemplate;

  /*
   * @since: 06/12/2022
   * description: Add access token + accountId to redis after logout or refresh token
   * @update:
   */
  public void blacklistJwt(String token, String accountId) {
    if (!isTokenBlacklisted(token)) {
      redisTemplate.opsForValue()
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
    return redisTemplate.opsForValue().get(RedisConstant.JWT + token) != null;
  }

  /*
   * @since: 25/09/2023
   * description: get all key using prefix key
   * @update:
   */
  public Set<String> getKeys(String pattern) {
    return redisTemplate.keys(pattern);
  }

  /*
   * @since: 25/09/2023
   * description: get value from key
   * @update:
   */
  public String getValue(String key) {
    return (String) redisTemplate.opsForValue().get(key);
  }

  /*
   * @since: 25/09/2023
   * description: set key + value redis
   * @update:
   */
  public void setValue(String key, String val) {
    redisTemplate.opsForValue().set(key, val, timeToLiveRedisSignOut, TimeUnit.HOURS);
  }

  /*
   * @since: 25/09/2023
   * description: delete key + value redis
   * @update:
   */
  public void remove(String key) {
    redisTemplate.delete(key);
  }
}
