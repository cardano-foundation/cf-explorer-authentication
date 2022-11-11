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

  /*
   * @author: phuc.nguyen5
   * @since: 4/11/2022
   * description: find wallet by stake address with deleted is false
   * @update:
   */
  Optional<WalletEntity> findByStakeAddressAndIsDeletedFalse(String stakeAddress);

  /*
   * @author: phuc.nguyen5
   * @since: 4/11/2022
   * description: check exist wallet by stake address with deleted is false
   * @update:
   */
  Boolean existsByStakeAddressAndIsDeletedFalse(String stakeAddress);

  /*
   * @author: phuc.nguyen5
   * @since: 9/11/2022
   * description: find wallet by user id with deleted is false
   * @update:
   */
  @Query("SELECT we FROM WalletEntity we WHERE we.user.id = :userId AND we.isDeleted = false")
  List<WalletEntity> findAllByUserId(@Param("userId") Long userId);
}
