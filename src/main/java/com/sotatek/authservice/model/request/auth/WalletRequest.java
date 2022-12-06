package com.sotatek.authservice.model.request.auth;

import com.sotatek.authservice.model.enums.ENetworkType;
import com.sotatek.authservice.model.enums.EWalletName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletRequest {

  @NotNull
  @NotBlank
  private String stakeAddress;

  @NotNull
  @NotBlank
  private EWalletName walletName;

  private String networkId;

  private ENetworkType networkType;
}
