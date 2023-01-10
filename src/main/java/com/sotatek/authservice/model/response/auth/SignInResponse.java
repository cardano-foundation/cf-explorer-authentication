package com.sotatek.authservice.model.response.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignInResponse {

  private String token;

  private String tokenType;

  private Long walletId;

  private String username;

  private String email;

  private String refreshToken;
}
