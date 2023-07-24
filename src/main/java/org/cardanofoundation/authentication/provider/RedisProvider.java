package org.cardanofoundation.authentication.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.constant.RedisConstant;
import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import org.cardanofoundation.explorer.common.utils.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class RedisProvider {

  private final RedisTemplate<String, Object> redisTemplate;

  /*
   * @since: 06/12/2022
   * description: Add access token + accountId to redis after logout or refresh token
   * @update:
   */
  public void blacklistJwt(String token, String accountId) {
    if (!isTokenBlacklisted(token)) {
      redisTemplate.opsForValue().set(RedisConstant.JWT + token, accountId);
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
}
