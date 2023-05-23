package org.cardanofoundation.authentication.model.response.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignInResponse {

  private String token;

  private String tokenType;

  private String email;

  private String refreshToken;

  private String address;
}
