package com.sotatek.authservice.model.response.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenResponse {

  private String accessToken;

  private String refreshToken;

  private String tokenType;
}
