package com.sotatek.authservice.exception;

import com.sotatek.authservice.exception.base.AbstractTokenException;
import com.sotatek.authservice.exception.enums.CommonErrorCode;

public class AccessTokenExpireException extends AbstractTokenException {

  public AccessTokenExpireException(String token) {
    super(token, CommonErrorCode.TOKEN_EXPIRED);
  }
}
