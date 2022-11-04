package com.sotatek.authservice.util;

import java.security.SecureRandom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NonceUtils {

  private static final SecureRandom random = new SecureRandom();

  /*
   * @author: phuc.nguyen5
   * @since: 21/10/2022
   * description: gen nonce value using random function
   * @update: 04/11/2022
   */
  public static String createNonce() {
    Integer nonce1 = random.nextInt(Integer.MAX_VALUE);
    Integer nonce2 = random.nextInt(Integer.MAX_VALUE);
    Integer nonce3 = random.nextInt(Integer.MAX_VALUE);
    return nonce1 + nonce2 + String.valueOf(nonce3);
  }
}
