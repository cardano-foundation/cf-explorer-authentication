package com.sotatek.authservice.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponse {

  private String token;

  private String tokenType;

  private Long walletId;

  private String username;

  private String email;

  private String refreshToken;
}
