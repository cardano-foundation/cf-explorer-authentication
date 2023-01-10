package com.sotatek.authservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisConstant {

  public static final String JWT = "jwt:blacklist:";
  public static final String SIGN_UP_CODE = "SignUp";
  public static final String RESET_PASSWORD_CODE = "Password";
}
