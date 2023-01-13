package com.sotatek.authservice.model.request.admin;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {

  @NotNull
  @NotBlank
  private String code;

  @NotNull
  @NotBlank
  private String password;
}
