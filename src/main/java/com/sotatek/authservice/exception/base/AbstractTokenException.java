package com.sotatek.authservice.exception.base;

import com.sotatek.authservice.exception.enums.CommonErrorCode;

public abstract class AbstractTokenException extends RuntimeException {

  private final String token;
  private final CommonErrorCode errorCode;

  public AbstractTokenException(String token, CommonErrorCode errorCode) {
    this.token = token;
    this.errorCode = errorCode;
  }

  public String getToken() {
    return this.token;
  }

  public CommonErrorCode getErrorCode() {
    return this.errorCode;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder("AbstractTokenException{");
    sb.append("token='").append(this.token).append('\'');
    sb.append(", errorCode=").append(this.errorCode);
    sb.append('}');
    return sb.toString();
  }
}
