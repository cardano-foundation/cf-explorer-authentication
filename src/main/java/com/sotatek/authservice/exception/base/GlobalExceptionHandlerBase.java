package com.sotatek.authservice.exception.base;

import com.sotatek.authservice.exception.AccessTokenExpireException;
import com.sotatek.authservice.exception.BusinessException;
import com.sotatek.authservice.exception.ErrorResponse;
import com.sotatek.authservice.exception.InvalidAccessTokenException;
import com.sotatek.authservice.exception.TokenRefreshException;
import com.sotatek.authservice.exception.ValidSignatureException;
import com.sotatek.authservice.exception.enums.CommonErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
public class GlobalExceptionHandlerBase {

  @ExceptionHandler({BusinessException.class})
  public ResponseEntity<ErrorResponse> handleException(BusinessException e) {
    log.warn("Business logic exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        ErrorResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getErrorMsg()).build());
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.warn("Unknown exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return new ResponseEntity<>(
        ErrorResponse.builder().errorCode(CommonErrorCode.UNKNOWN_ERROR.getServiceErrorCode())
            .errorMessage(CommonErrorCode.UNKNOWN_ERROR.getDesc()).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({TokenRefreshException.class})
  public ResponseEntity<ErrorResponse> handleException(TokenRefreshException e) {
    log.warn("refresh token exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        ErrorResponse.builder().errorCode(e.getErrorCode().getServiceErrorCode())
            .errorMessage(e.getErrorCode().getDesc()).build());
  }

  @ExceptionHandler({ValidSignatureException.class})
  public ResponseEntity<ErrorResponse> handleAuthException(ValidSignatureException e) {
    log.warn("Authentication exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        ErrorResponse.builder().errorCode(e.getErrCode()).errorMessage(e.getErrMessage()).build());
  }

  @ExceptionHandler({AccessTokenExpireException.class})
  public ResponseEntity<ErrorResponse> handleAuthException(AccessTokenExpireException e) {
    log.warn("Authentication exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        ErrorResponse.builder().errorCode(e.getErrorCode().getServiceErrorCode())
            .errorMessage(e.getErrorCode().getDesc()).build());
  }

  @ExceptionHandler({InvalidAccessTokenException.class})
  public ResponseEntity<ErrorResponse> handleAuthException(InvalidAccessTokenException e) {
    log.warn("Invalid access token: {}", e.getErrorCode());
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        ErrorResponse.builder().errorCode(e.getErrorCode().getServiceErrorCode())
            .errorMessage(e.getErrorCode().getDesc()).build());
  }
}
