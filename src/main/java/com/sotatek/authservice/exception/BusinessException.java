package com.sotatek.authservice.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BusinessException extends RuntimeException {

  private String errorCode;

  private String errorMsg;
}
