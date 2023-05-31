package org.cardanofoundation.authentication.repository;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import org.cardanofoundation.authentication.model.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

  Optional<RefreshTokenEntity> findByToken(String token);

  @Query(value = "SELECT rf FROM RefreshTokenEntity rf "
      + "JOIN UserEntity ue ON rf.user.id = ue.id "
      + "WHERE ue.id = :userId")
  List<RefreshTokenEntity> findALlByUserId(@Param("userId") Long userId);
}
