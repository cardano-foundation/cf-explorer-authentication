package com.sotatek.authservice.model.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class RefreshTokenEntity extends BaseEntity {

  @Column(name = "token", nullable = false, unique = true)
  private String token;

  @Column(name = "expiry_date", nullable = false)
  private Instant expiryDate;

  @Column(name = "stake_address", nullable = false)
  private String stakeAddress;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @EqualsAndHashCode.Exclude
  private UserEntity user;
}
