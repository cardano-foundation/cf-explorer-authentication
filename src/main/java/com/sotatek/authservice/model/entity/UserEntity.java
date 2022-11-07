package com.sotatek.authservice.model.entity;


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
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity {

  @Column(name = "username", length = 64, unique = true, nullable = false)
  @NotNull
  private String username;

  @Column(name = "email", length = 64)
  @NotNull
  private String email;

  @Column(name = "phone", length = 20)
  @NotNull
  private String phone;

  @Column(name = "avatar")
  private String avatar;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
  @NotNull
  private boolean isDeleted;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> roles = new HashSet<>();
}
