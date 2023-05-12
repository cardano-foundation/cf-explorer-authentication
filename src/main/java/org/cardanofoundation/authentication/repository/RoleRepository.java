package org.cardanofoundation.authentication.repository;

import org.cardanofoundation.authentication.model.entity.RoleEntity;
import org.cardanofoundation.authentication.model.enums.ERole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByName(ERole name);
}
