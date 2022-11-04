package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByUsername(String username);

  Boolean existsByUsername(String username);

//  Boolean existsByPublicAddress(String email);

//  Optional<UserEntity> findByPublicAddress(String publicAddress);

  @Query("SELECT ue FROM UserEntity ue INNER JOIN WalletEntity we ON ue.id = we.user.id AND we.stakeAddress = :stakeAddress")
  Optional<UserEntity> findByStakeAddress(@Param("stakeAddress") String stakeAddress);

}
