package com.sotatek.authservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthConstant {

  public static final String BASE_AUTH_PATH = "/api/auth/";

  public static final String BASE_USER_PATH = "/api/user/";

  public static final String[] CSRF_TOKEN_PATH = {"/api/csrf"};

  public static final String[] AUTH_WHITELIST = {BASE_AUTH_PATH + "sign-in",
      BASE_AUTH_PATH + "sign-up", BASE_AUTH_PATH + "refresh-token", BASE_AUTH_PATH + "sign-out",
      BASE_AUTH_PATH + "transfers-wallet"};

  public static final String[] USER_WHITELIST = {BASE_USER_PATH + "get-nonce"};

  public static final String[] DOCUMENT_WHITELIST = {"/v3/api-docs/**", "/planning/",
      "/swagger-ui/**", "/swagger-ui.html"};

  public static final String[] CLIENT_WHITELIST = {"/", "/error", "/favicon.ico", "/**/*.png",
      "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.scss", "/**/*.js"};

}
