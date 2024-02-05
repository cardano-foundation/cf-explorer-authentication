package org.cardanofoundation.authentication.model.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {

  private String address;

  private String signature;

  @Email private String email;

  private String password;

  @NotNull
  @Max(1)
  @Min(0)
  private Integer type;
}
