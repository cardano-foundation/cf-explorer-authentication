package com.sotatek.authservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthConstant {

  public static final String BASE_AUTH_PATH = "/api/v1/auth/";

  public static final String BASE_ADMIN_PATH = "/api/v1/admin/";

  public static final String BASE_USER_PATH = "/api/v1/user/";

  public static final String[] CSRF_TOKEN_PATH = {"/api/v1/csrf"};

  public static final String[] AUTH_WHITELIST = {BASE_AUTH_PATH + "sign-in",
      BASE_AUTH_PATH + "sign-up", BASE_AUTH_PATH + "refresh-token", BASE_AUTH_PATH + "sign-out",
      BASE_AUTH_PATH + "transfers-wallet", BASE_ADMIN_PATH + "sign-up", BASE_ADMIN_PATH + "verify"};

  public static final String[] USER_WHITELIST = {BASE_USER_PATH + "get-nonce"};

  public static final String[] DOCUMENT_WHITELIST = {"/v3/api-docs/**", "/planning/",
      "/swagger-ui/**", "/swagger-ui.html"};

  public static final String[] CLIENT_WHITELIST = {"/", "/error", "/favicon.ico", "/**/*.png",
      "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.scss", "/**/*.js"};

}
