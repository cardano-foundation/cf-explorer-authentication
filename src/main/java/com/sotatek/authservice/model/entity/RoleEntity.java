package com.sotatek.authservice.model.entity;

import com.sotatek.authservice.model.enums.ERole;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class RoleEntity extends BaseEntity {

  @Column(name = "name", length = 64, unique = true, nullable = false)
  @NotNull
  @Enumerated(EnumType.STRING)
  private ERole name;

  @Column(name = "description", length = 256)
  @NotNull
  private String description;
}
