package com.sotatek.authservice.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInRequest {

  private String ipAddress;

  private String stakeAddress;

  @NotBlank
  @NotNull
  private String signature;
}
