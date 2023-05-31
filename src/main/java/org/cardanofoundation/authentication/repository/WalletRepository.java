package org.cardanofoundation.authentication.repository;

import org.cardanofoundation.authentication.model.entity.WalletEntity;
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
      + "WHERE we.address = :address")
  Optional<WalletEntity> findWalletByAddress(@Param("address") String address);

  /*
   * @author: phuc.nguyen5
   * @since: 19/05/2023
   * description: find address by userId
   * @update:
   */
  @Query(value = "SELECT we.address FROM UserEntity ue "
      + "JOIN WalletEntity we ON ue.id = we.user.id "
      + "WHERE ue.id = :userId")
  String findAddressByUserId(@Param("userId") Long userId);
}
