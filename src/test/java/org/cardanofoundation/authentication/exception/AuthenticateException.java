package org.cardanofoundation.authentication.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticateException extends AuthenticationException {

  public AuthenticateException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public AuthenticateException(String msg) {
    super(msg);
  }
}
