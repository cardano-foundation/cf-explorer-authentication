package com.sotatek.authservice.exception;

import com.sotatek.authservice.exception.base.AbstractTokenException;
import com.sotatek.authservice.exception.enums.CommonErrorCode;

public class InvalidAccessTokenException extends AbstractTokenException {

  public InvalidAccessTokenException(String accessToken) {
    super(accessToken, CommonErrorCode.INVALID_TOKEN);
  }
}
