package com.sotatek.authservice.model.request.admin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
