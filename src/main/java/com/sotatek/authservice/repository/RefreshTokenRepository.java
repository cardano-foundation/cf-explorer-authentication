package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.RefreshTokenEntity;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

  @NotNull
  @Override
  Optional<RefreshTokenEntity> findById(@NotNull Long id);

  Optional<RefreshTokenEntity> findByToken(String token);

  @Query(value = "SELECT rf FROM RefreshTokenEntity rf "
      + "JOIN UserEntity ue ON rf.user.id = ue.id "
      + "WHERE ue.username = :username")
  List<RefreshTokenEntity> findALlByUsername(@Param("username") String username);
}
