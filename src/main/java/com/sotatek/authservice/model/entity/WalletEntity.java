package com.sotatek.authservice.model.entity;

import com.sotatek.authservice.model.enums.ENetworkType;
import com.sotatek.authservice.model.enums.EWalletName;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@Entity
@Table(name = "wallet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletEntity extends BaseEntity {

  @Column(name = "address", nullable = false, unique = true)
  private String address;

  @Enumerated(EnumType.STRING)
  @Column(name = "wallet_name")
  private EWalletName walletName;

  @Column(name = "nonce", nullable = false)
  private String nonce;

  @Column(name = "nonce_encode", nullable = false)
  private String nonceEncode;

  @Column(name = "expiry_date_nonce", nullable = false)
  private Instant expiryDateNonce;

  @Column(name = "network_id")
  private String networkId;

  @Column(name = "network_type")
  @Enumerated(EnumType.STRING)
  private ENetworkType networkType;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @EqualsAndHashCode.Exclude
  private UserEntity user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    WalletEntity wallet = (WalletEntity) o;
    return getId() != null && Objects.equals(getId(), wallet.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
