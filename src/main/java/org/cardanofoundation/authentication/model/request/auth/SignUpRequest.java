package org.cardanofoundation.authentication.model.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

  @NotNull
  @Email
  private String email;

  @NotNull
  private String password;
}
