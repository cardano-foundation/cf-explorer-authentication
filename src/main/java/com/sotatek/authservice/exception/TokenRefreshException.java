package com.sotatek.authservice.exception;

import com.sotatek.authservice.exception.base.AbstractTokenException;
import com.sotatek.authservice.exception.enums.CommonErrorCode;

public class TokenRefreshException extends AbstractTokenException {

  public TokenRefreshException(String token, CommonErrorCode errorCode) {
    super(token, errorCode);
  }
}
