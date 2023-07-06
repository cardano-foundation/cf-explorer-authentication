package org.cardanofoundation.authentication.controller.advice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.explorer.common.exceptions.AccessTokenExpireException;
import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.ErrorResponse;
import org.cardanofoundation.explorer.common.exceptions.IgnoreRollbackException;
import org.cardanofoundation.explorer.common.exceptions.InvalidAccessTokenException;
import org.cardanofoundation.explorer.common.exceptions.TokenRefreshException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({BusinessException.class})
  public ResponseEntity<ErrorResponse> handleException(BusinessException e) {
    log.error("Business logic exception: {}", e.getErrorMsg());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        ErrorResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getErrorMsg()).build());
  }

  @ExceptionHandler({IgnoreRollbackException.class})
  public ResponseEntity<ErrorResponse> handleException(IgnoreRollbackException e) {
    log.error("No rollback exception: {}", e.getErrorMsg());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        ErrorResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getErrorMsg()).build());
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("Unknown exception: {}", e.getMessage());
    return new ResponseEntity<>(
        ErrorResponse.builder().errorCode(CommonErrorCode.UNKNOWN_ERROR.getServiceErrorCode())
            .errorMessage(CommonErrorCode.UNKNOWN_ERROR.getDesc()).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(Throwable e) {
    log.error("Unknown exception: {}", e.getMessage());
    return new ResponseEntity<>(
        ErrorResponse.builder().errorCode(CommonErrorCode.UNKNOWN_ERROR.getServiceErrorCode())
            .errorMessage(CommonErrorCode.UNKNOWN_ERROR.getDesc()).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({TokenRefreshException.class})
  public ResponseEntity<ErrorResponse> handleAuthException(TokenRefreshException e) {
    log.error("Refresh token exception: {}, stack trace:", e.getErrorCode());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        ErrorResponse.builder().errorCode(e.getErrorCode().getServiceErrorCode())
            .errorMessage(e.getErrorCode().getDesc()).build());
  }

  @ExceptionHandler({AccessTokenExpireException.class})
  public ResponseEntity<ErrorResponse> handleAuthException(AccessTokenExpireException e) {
    log.error("Access token expired: {}, stack trace:", e.getErrorCode());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        ErrorResponse.builder().errorCode(e.getErrorCode().getServiceErrorCode())
            .errorMessage(e.getErrorCode().getDesc()).build());
  }

  @ExceptionHandler({InvalidAccessTokenException.class})
  public ResponseEntity<ErrorResponse> handleAuthException(InvalidAccessTokenException e) {
    log.error("Invalid access token: {}", e.getErrorCode());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        ErrorResponse.builder().errorCode(e.getErrorCode().getServiceErrorCode())
            .errorMessage(e.getErrorCode().getDesc()).build());
  }

  @ExceptionHandler({ConstraintViolationException.class,
      MissingServletRequestParameterException.class, HttpMediaTypeNotSupportedException.class,
      MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      Exception e) {
    log.warn("constraint not valid: {}", e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        ErrorResponse.builder().errorCode(CommonErrorCode.INVALID_PARAM.getCode())
            .errorMessage(CommonErrorCode.INVALID_PARAM.getDesc()).build());
  }
}
