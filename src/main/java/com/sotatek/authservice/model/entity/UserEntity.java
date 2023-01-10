package com.sotatek.authservice.model.entity;


import com.sotatek.authservice.model.enums.EStatus;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class UserEntity extends BaseEntity {

  @Column(name = "username", length = 64, nullable = false, unique = true)
  @NotNull
  private String username;

  @Column(name = "email", length = 64)
  private String email;

  @Column(name = "avatar")
  private String avatar;

  @Column(name = "password", length = 256)
  private String password;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private EStatus status;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  @EqualsAndHashCode.Exclude
  private Set<RoleEntity> roles = new HashSet<>();
}
