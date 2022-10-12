package com.sotatek.authservice.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignOutRequest {

  @NotNull
  @NotBlank
  private String accessToken;

  @NotNull
  @NotBlank
  private String refreshToken;

  @NotNull
  @NotBlank
  private String username;

  private String ipAddress;

}
