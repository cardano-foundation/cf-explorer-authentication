package org.cardanofoundation.authentication.model.request.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInAdminRequest {

  @NotBlank
  @NotNull
  @Email
  private String email;

  @NotBlank
  @NotNull
  private String password;
}
