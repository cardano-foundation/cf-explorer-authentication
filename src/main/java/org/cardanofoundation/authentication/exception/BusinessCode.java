package org.cardanofoundation.authentication.exception;

import org.cardanofoundation.explorer.common.exception.ErrorCode;

public enum BusinessCode implements ErrorCode {
  TOKEN_NOT_FOUND("2", "The token is not found"),
  REFRESH_TOKEN_INVALID("3", "The refresh token is invalid"),
  REFRESH_TOKEN_EXPIRED("4", "The refresh token is expired"),
  SIGNATURE_INVALID("5", "The signature is invalid"),
  SIGNATURE_NOT_VERIFIED("6", "The signature is not verified"),
  LIMIT_BOOKMARK_IS_2000("14", "Bookmarks do not exceed 2000 records"),
  BOOKMARK_IS_EXIST("16", "Bookmark is already exists"),
  INVALID_VERIFY_CODE("21", "The verify code is invalid"),
  VERIFY_CODE_NOT_PENDING("22", "User is not ready to active"),
  EMAIL_IS_ALREADY_EXIST("23", "Email is already exist"),
  USERNAME_OR_PASSWORD_INVALID("24", "Username or password is invalid"),
  KEY_OR_SIGNATURE_MUST_NOT_BE_NULL("1", "The signature and the key must not be null");

  private final String code;
  private final String desc;

  private BusinessCode(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public String getDesc() {
    return this.desc;
  }

  @Override
  public String getServicePrefix() {
    return "COMMON_ERROR";
  }
}
