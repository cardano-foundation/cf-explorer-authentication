package org.cardanofoundation.authentication.model.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.enums.EWalletName;

@Getter
@Setter
public class WalletRequest {

  @NotNull
  @NotBlank
  private String address;

  @NotNull
  @NotBlank
  private EWalletName walletName;

  private String networkId;

  private ENetworkType networkType;

  @NotNull
  private String signature;
}
