package com.sotatek.authservice.model.request.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransfersWalletRequest {

  @NotNull
  private WalletRequest wallet;

  @NotNull
  @NotBlank
  private String refreshJwt;
}
