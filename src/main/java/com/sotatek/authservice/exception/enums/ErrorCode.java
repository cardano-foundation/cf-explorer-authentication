package com.sotatek.authservice.exception.enums;

public interface ErrorCode {

  String getCode();

  String getDesc();

  String getServicePrefix();

  default String getServiceErrorCode() {
    String var10000 = this.getServicePrefix();
    return var10000 + "_" + this.getCode();
  }
}
