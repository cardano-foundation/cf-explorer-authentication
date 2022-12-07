package com.sotatek.authservice.model.request.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransfersWalletRequest {

  @NotNull
  @NotBlank
  private String username;

  @NotNull
  private WalletRequest wallet;

  @NotNull
  @NotBlank
  private String refreshToken;
}
