package com.sotatek.authservice.model.request.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

  @NotNull
  private String username;

  @NotNull
  @Email
  private String email;

  @NotNull
  private String password;
}
