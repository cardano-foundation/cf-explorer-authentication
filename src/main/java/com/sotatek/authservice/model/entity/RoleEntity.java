package com.sotatek.authservice.model.entity;

import com.sotatek.authservice.model.enums.ERole;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity extends BaseEntity {

  @Column(name = "name", length = 64, unique = true, nullable = false)
  @NotNull
  @Enumerated(EnumType.STRING)
  private ERole name;

  @Column(name = "description", length = 256)
  @NotNull
  private String description;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RoleEntity role = (RoleEntity) o;

    return new EqualsBuilder().append(name, role.name).append(description, role.description)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(name).append(description).toHashCode();
  }
}
