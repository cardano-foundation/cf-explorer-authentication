package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.WalletEntity;
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
   * description: find wallet by stake address
   * @update: 05/12/2022
   */
  @Query(value = "SELECT we FROM UserEntity ue "
      + "JOIN WalletEntity we ON ue.id = we.user.id "
      + "WHERE we.stakeAddress = :stakeAddress")
  Optional<WalletEntity> findWalletByStakeAddress(String stakeAddress);

  /*
   * @author: phuc.nguyen5
   * @since: 4/11/2022
   * description: check exist wallet by stake address
   * @update: 05/12/2022
   */
  Boolean existsByStakeAddress(String stakeAddress);

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: get address wallet by id
   * @update:
   */
  @Query(value = "SELECT we.stakeAddress FROM WalletEntity we WHERE we.id = :walletId")
  String getAddressWalletById(@Param("walletId") Long walletId);
}
