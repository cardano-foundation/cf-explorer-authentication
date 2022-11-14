package com.sotatek.authservice.model.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

  @Column(name = "stake_address", nullable = false)
  private String stakeAddress;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private UserEntity user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RefreshTokenEntity that = (RefreshTokenEntity) o;

    return new EqualsBuilder().append(token, that.token).append(expiryDate, that.expiryDate)
        .append(stakeAddress, that.stakeAddress).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(token).append(expiryDate).append(stakeAddress)
        .toHashCode();
  }
}
