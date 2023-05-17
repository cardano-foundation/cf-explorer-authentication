package org.cardanofoundation.authentication.model.request.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {

  private String address;

  private String signature;

  private String username;

  private String password;

  @NotNull
  private Integer type;
}
