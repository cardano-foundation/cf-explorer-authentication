package com.sotatek.authservice.model.request;

import com.sotatek.authservice.model.enums.ENetworkType;
import com.sotatek.authservice.model.enums.EWalletName;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletRequest extends BaseRequest {

  @NotNull
  @NotBlank
  private String stakeAddress;

  @NotNull
  @NotBlank
  private EWalletName walletName;

  private BigDecimal balanceAtLogin;

  private String networkId;

  private ENetworkType networkType;
}
