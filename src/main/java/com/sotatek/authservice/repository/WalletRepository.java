package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.WalletEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

  Optional<WalletEntity> findByStakeAddressAndIsDeletedFalse(String stakeAddress);

  Boolean existsByStakeAddressAndIsDeletedFalse(String stakeAddress);

  @Query("SELECT we FROM WalletEntity we WHERE we.user.id = :userId AND we.isDeleted = false")
  List<WalletEntity> findAllByUserId(@Param("userId") Long userId);
}
