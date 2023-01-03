package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  /*
   * @author: phuc.nguyen5
   * @since: 26/10/2022
   * description: find user by username
   * @update: 05/12/2022
   */
  Optional<UserEntity> findByUsername(String username);

  /*
   * @author: phuc.nguyen5
   * @since: 26/10/2022
   * description: check exist user by username
   * @update: 05/12/2022
   */
  Boolean existsByUsername(String username);

  /*
   * @author: phuc.nguyen5
   * @since: 26/10/2022
   * description: find user by stake address wallet
   * @update: 05/12/2022
   */
  @Query("SELECT ue FROM UserEntity ue "
      + "JOIN WalletEntity we ON ue.id = we.user.id "
      + "WHERE we.stakeAddress = :stakeAddress")
  Optional<UserEntity> findUserByStakeAddress(@Param("stakeAddress") String stakeAddress);

}
