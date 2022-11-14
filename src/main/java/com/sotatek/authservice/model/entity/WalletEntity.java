package com.sotatek.authservice.model.entity;

import com.sotatek.authservice.model.enums.ENetworkType;
import com.sotatek.authservice.model.enums.EWalletName;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "wallet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletEntity extends BaseEntity {

  @Column(name = "stake_address", nullable = false)
  private String stakeAddress;

  @Enumerated(EnumType.STRING)
  @Column(name = "wallet_name")
  private EWalletName walletName;

  @Column(name = "nonce", nullable = false)
  private String nonce;

  @Column(name = "nonce_encode", nullable = false)
  private String nonceEncode;

  @Column(name = "expiry_date_nonce", nullable = false)
  private Instant expiryDateNonce;

  @Column(name = "balance_at_login")
  private BigDecimal balanceAtLogin;

  @Column(name = "network_id")
  private String networkId;

  @Column(name = "network_type")
  @Enumerated(EnumType.STRING)
  private ENetworkType networkType;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
  @NotNull
  private boolean isDeleted;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    WalletEntity wallet = (WalletEntity) o;

    return new EqualsBuilder().append(isDeleted, wallet.isDeleted)
        .append(stakeAddress, wallet.stakeAddress).append(walletName, wallet.walletName)
        .append(nonce, wallet.nonce).append(nonceEncode, wallet.nonceEncode)
        .append(expiryDateNonce, wallet.expiryDateNonce)
        .append(balanceAtLogin, wallet.balanceAtLogin).append(networkId, wallet.networkId)
        .append(networkType, wallet.networkType).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(stakeAddress).append(walletName).append(nonce)
        .append(nonceEncode).append(expiryDateNonce).append(balanceAtLogin).append(networkId)
        .append(networkType).append(isDeleted).toHashCode();
  }
}
