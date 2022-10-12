package com.sotatek.authservice.util;

import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NonceUtils {

  private static final Random random = new Random();

  /*
   * @author: phuc.nguyen5
   * @since: 21/10/2022
   * description: gen nonce value using random function
   * @update:
   */
  public static String createNonce() {
    Integer nonce1 = random.nextInt(Integer.MAX_VALUE);
    Integer nonce2 = random.nextInt(Integer.MAX_VALUE);
    return nonce1 + String.valueOf(nonce2);
  }
}
