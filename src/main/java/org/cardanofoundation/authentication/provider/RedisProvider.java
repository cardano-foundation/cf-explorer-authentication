package org.cardanofoundation.authentication.provider;

import org.cardanofoundation.authentication.constant.RedisConstant;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import com.sotatek.cardanocommonapi.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class RedisProvider {

  private final RedisTemplate<String, Object> redisTemplate;

  /*
   * @author: phuc.nguyen5
   * @since: 06/12/2022
   * description: Add access token + username to redis after logout or refresh token
   * @update:
   */
  public void blacklistJwt(String token, String username) {
    if (!isTokenBlacklisted(token)) {
      redisTemplate.opsForValue().set(RedisConstant.JWT + token, username);
    }
  }

  /*
   * @author: phuc.nguyen5
   * @since: 06/12/2022
   * description: Check exist access token + username from redis
   * @update:
   */
  public boolean isTokenBlacklisted(String token) {
    if (Boolean.TRUE.equals(StringUtils.isNullOrEmpty(token))) {
      throw new BusinessException(CommonErrorCode.INVALID_TOKEN);
    }
    return redisTemplate.opsForValue().get(RedisConstant.JWT + token) != null;
  }
}
