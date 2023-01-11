package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.EStatus;
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
      + "WHERE we.address = :address")
  Optional<UserEntity> findUserByWalletAddress(@Param("address") String address);

  /*
   * @author: phuc.nguyen5
   * @since: 7/1/2023
   * description: check exist user by email
   * @update:
   */
  Boolean existsByEmail(String email);

  /*
   * @author: phuc.nguyen5
   * @since: 10/01/2023
   * description: find user by email
   * @update:
   */
  Optional<UserEntity> findByEmailAndStatus(String email, EStatus status);


  /*
   * @author: phuc.nguyen5
   * @since: 11/01/2023
   * description: find user by username
   * @update:
   */
  Optional<UserEntity> findByUsernameAndStatus(String username, EStatus status);
}
