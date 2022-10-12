package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.RoleEntity;
import com.sotatek.authservice.model.enums.ERole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByName(ERole name);
}
