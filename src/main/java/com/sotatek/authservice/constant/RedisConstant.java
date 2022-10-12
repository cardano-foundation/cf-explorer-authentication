package com.sotatek.authservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisConstant {

  public static final String JWT = "jwt:blacklist:";
  public static final String FAILED_ATTEMPT = "login:attempt:";
  public static final String RESET_PW = "reset:pw:";
}
