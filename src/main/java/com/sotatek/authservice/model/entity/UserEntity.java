package com.sotatek.authservice.model.entity;


import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user", schema = "local1")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity {

  @Column(name = "username", length = 64, unique = true, nullable = false)
  @NotNull
  private String username;

  @Column(name = "public_address", length = 64, unique = true, nullable = false)
  @NotNull
  private String publicAddress;

  @Column(name = "email", length = 64, nullable = false)
  @NotNull
  private String email;

  @Column(name = "nonce", nullable = false, length = 64)
  @NotNull
  private String nonce;

  @Column(name = "nonce_encode", nullable = false, length = 256)
  private String nonceEncode;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
  @NotNull
  private boolean isDeleted;

  @Column(name = "expiry_date_nonce", nullable = false)
  private Instant expiryDateNonce;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> roles = new HashSet<>();
}
