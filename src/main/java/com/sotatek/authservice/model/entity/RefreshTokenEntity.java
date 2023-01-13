package com.sotatek.authservice.model.entity;

import java.time.Instant;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "refresh_token")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntity extends BaseEntity {

  @Column(name = "token", nullable = false, unique = true)
  private String token;

  @Column(name = "expiry_date", nullable = false)
  private Instant expiryDate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "wallet_id")
  @EqualsAndHashCode.Exclude
  private WalletEntity wallet;

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
    RefreshTokenEntity that = (RefreshTokenEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
