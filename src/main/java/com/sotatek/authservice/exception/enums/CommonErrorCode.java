package com.sotatek.authservice.exception.enums;

public enum CommonErrorCode implements ErrorCode {

  UNKNOWN_ERROR("1", "Unknown error"), INVALID_TOKEN("2",
      "The access token is invalid"), TOKEN_EXPIRED("3",
      "The access token is expired"), REFRESH_TOKEN_EXPIRED("4",
      "Refresh token is expired"), SIGNATURE_INVALID("5",
      "Signature is invalid"), USER_IS_NOT_EXIST("6", "User is not exist"), NONCE_EXPIRED("7",
      "The nonce is expired. Please take new nonce"), ROLE_IS_NOT_FOUND("8",
      "Role is not found"), REFRESH_TOKEN_IS_NOT_EXIST("9",
      "Refresh token is not exist"), TOKEN_INVALID_SIGNATURE("10",
      "The signature access token is invalid"), TOKEN_UNSUPPORTED("11",
      "The access token is unsupported"), TOKEN_IS_NOT_EMPTY("12", "The access token is not empty");

  private final String code;
  private final String desc;

  private CommonErrorCode(String code, String desc) {
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
    return "CC";
  }
}
