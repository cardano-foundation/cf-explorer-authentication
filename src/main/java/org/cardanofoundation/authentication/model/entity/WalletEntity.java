package org.cardanofoundation.authentication.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
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

  @Column(name = "wallet_name")
  private String walletName;

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

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
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
