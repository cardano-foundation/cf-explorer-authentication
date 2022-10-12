package com.sotatek.authservice.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Data
@Builder
public class ValidSignatureException extends RuntimeException {

  private String errCode;

  private String errMessage;
}
