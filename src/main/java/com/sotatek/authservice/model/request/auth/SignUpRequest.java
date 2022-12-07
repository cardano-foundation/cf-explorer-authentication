package com.sotatek.authservice.model.request.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

  @NotNull
  @NotBlank
  private String username;

  @NotNull
  private WalletRequest wallet;
}
