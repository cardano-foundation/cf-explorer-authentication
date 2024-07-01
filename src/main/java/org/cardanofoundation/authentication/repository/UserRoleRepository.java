package org.cardanofoundation.authentication.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.cardanofoundation.explorer.common.entity.explorer.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
  @Query(value = """
    SELECT distinct ur.userId FROM UserRole ur where ur.roleId = :roleId
""")
  Set<String> findByRoleId(@Param("roleId") String roleId);
}
