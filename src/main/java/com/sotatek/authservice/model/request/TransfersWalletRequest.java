package com.sotatek.authservice.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransfersWalletRequest extends BaseRequest {

  @NotNull
  @NotBlank
  private String username;

  @NotNull
  private WalletRequest wallet;

  @NotNull
  @NotBlank
  private String refreshToken;
}
