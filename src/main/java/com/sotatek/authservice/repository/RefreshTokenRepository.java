package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.RefreshTokenEntity;
import java.util.Optional;
import javax.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

  @NotNull
  @Override
  Optional<RefreshTokenEntity> findById(@NotNull Long id);

  Optional<RefreshTokenEntity> findByToken(String token);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("DELETE FROM RefreshTokenEntity rt WHERE rt.user.id = :userId")
  int deleteByUserId(@Param("userId") Long userId);
}
