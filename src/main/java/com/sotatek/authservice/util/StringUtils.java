package com.sotatek.authservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

  /*
   * @author: phuc.nguyen5
   * @since: 24/10/2022
   * description: validate string null and empty
   * @update:
   */
  public static Boolean isNullOrEmpty(String str) {
    return str == null || str.trim().length() == 0;
  }
}
