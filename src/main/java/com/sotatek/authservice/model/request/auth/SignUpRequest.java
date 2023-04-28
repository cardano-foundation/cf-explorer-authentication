package com.sotatek.authservice.model.request.auth;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

  @NotNull
  private String username;

  private String email;

  @NotNull
  private String password;
}
