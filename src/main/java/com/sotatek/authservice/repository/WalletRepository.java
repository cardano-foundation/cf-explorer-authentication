package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.WalletEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

  Optional<WalletEntity> findByStakeAddress(String stakeAddress);

  Boolean existsByStakeAddress(String stakeAddress);
}
