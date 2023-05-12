package org.cardanofoundation.authentication.model.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
