package com.sotatek.authservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonConstant {

  public static final Integer LIMIT_BOOKMARK = 2000;

  public static final Integer LIMIT_NOTE = 2000;

  public static final String TOKEN_TYPE = "Bearer";

  public static final String RESPONSE_SUCCESS = "Success";
}
