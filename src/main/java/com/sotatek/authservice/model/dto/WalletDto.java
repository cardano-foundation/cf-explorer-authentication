package com.sotatek.authservice.model.dto;

import com.sotatek.authservice.model.enums.ENetworkType;
import com.sotatek.authservice.model.enums.EWalletName;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletDto {

  private String stakeAddress;

  private EWalletName walletName;

  private BigDecimal balanceAtLogin;

  private String networkId;

  private ENetworkType networkType;

}
