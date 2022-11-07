package com.sotatek.authservice.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenRequest {

  @NotBlank
  @NotNull
  private String refreshToken;
}
