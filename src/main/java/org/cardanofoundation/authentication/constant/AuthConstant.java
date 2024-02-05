package org.cardanofoundation.authentication.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthConstant {

  public static final String BASE_AUTH_PATH = "/api/v1/auth/";

  public static final String BASE_USER_PATH = "/api/v1/user/";

  public static final String BASE_VERIFY_PATH = "/api/v1/verify/";

  public static final String[] AUTH_WHITELIST = {
    BASE_AUTH_PATH + "sign-in",
    BASE_AUTH_PATH + "sign-up",
    BASE_AUTH_PATH + "refresh-token",
    BASE_AUTH_PATH + "sign-out",
    BASE_AUTH_PATH + "get-nonce***",
    BASE_VERIFY_PATH + "active",
    BASE_VERIFY_PATH + "forgot-password",
    BASE_VERIFY_PATH + "reset-password",
    BASE_VERIFY_PATH + "expired-code"
  };

  public static final String[] USER_WHITELIST = {
    BASE_USER_PATH + "exist-email", BASE_USER_PATH + "role-mapping"
  };

  public static final String[] DOCUMENT_WHITELIST = {
    "/v3/api-docs/**", "/planning/", "/swagger-ui/**", "/swagger-ui.html"
  };

  public static final String[] CLIENT_WHITELIST = {
    "/",
    "/error",
    "/favicon.ico",
    "/**/*.png",
    "/**/*.gif",
    "/**/*.svg",
    "/**/*.jpg",
    "/**/*.html",
    "/**/*.css",
    "/**/*.scss",
    "/**/*.js"
  };
}
