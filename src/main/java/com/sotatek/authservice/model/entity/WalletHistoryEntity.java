package com.sotatek.authservice.model.entity;

import com.sotatek.authservice.model.enums.EWalletAction;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "wallet_history")
@Getter
@Setter
@NoArgsConstructor
public class WalletHistoryEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "wallet_action")
  @Enumerated(EnumType.STRING)
  private EWalletAction walletAction;

  @Column(name = "ip_address")
  private String ipAddress;

  @Column(name = "action_time")
  private Instant actionTime;

  @Column(name = "is_success")
  private Boolean isSuccess;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "wallet_id")
  private WalletEntity wallet;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    WalletHistoryEntity that = (WalletHistoryEntity) o;

    return new EqualsBuilder().append(id, that.id).append(walletAction, that.walletAction)
        .append(ipAddress, that.ipAddress).append(actionTime, that.actionTime)
        .append(isSuccess, that.isSuccess).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(id).append(walletAction).append(ipAddress)
        .append(actionTime).append(isSuccess).toHashCode();
  }
}
