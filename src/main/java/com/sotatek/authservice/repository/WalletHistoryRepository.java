package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.WalletHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletHistoryRepository extends JpaRepository<WalletHistoryEntity, Long> {

}
