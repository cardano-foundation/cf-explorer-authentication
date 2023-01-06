package com.sotatek.authservice.model.request.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {

  @NotBlank
  @NotNull
  private String address;

  @NotBlank
  @NotNull
  private String signature;

  @NotBlank
  @NotNull
  private String ipAddress;
}
