package org.cardanofoundation.authentication.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonConstant {

  public static final Integer LIMIT_BOOKMARK = 2000;

  public static final String TOKEN_TYPE = "Bearer";

  public static final String RESPONSE_SUCCESS = "Success";

  public static final String CODE_SUCCESS = "SS_0";

  public static final String CODE_FAILURE = "SS_1";

  public static final String RESPONSE_FAILURE = "Failure";

  public static final Integer SLEEP_TIME = 1000;

  public static final String PREFIXED_ADDRESS = "stake";

  public static final int ZERO = 0;

  public static final String ATTRIBUTE_NONCE = "nonce";

  public static final String ATTRIBUTE_WALLET_NAME = "walletName";

  public static final String ATTRIBUTE_LOGIN_TIME = "loginTime";

  public static final String ATTRIBUTE_BOOKMARK = "BOOKMARK_";

  public static final String ATTRIBUTE_BOOKMARK_ADD_TIME = "{AddedOn=";
}
