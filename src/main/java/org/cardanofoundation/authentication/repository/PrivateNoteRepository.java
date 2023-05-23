package org.cardanofoundation.authentication.repository;

import org.cardanofoundation.authentication.model.entity.PrivateNoteEntity;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateNoteRepository extends JpaRepository<PrivateNoteEntity, Long> {

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: get count note by user
   * @update:
   */
  @Query(value = "SELECT count(pn.id) FROM PrivateNoteEntity pn WHERE pn.user.id = :userId AND pn.network = :network")
  Integer getCountNoteByUser(@Param("userId") Long userId, @Param("network")
  ENetworkType network);

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: check exist note
   * @update:
   */
  @Query(value = "SELECT pn.id FROM PrivateNoteEntity pn WHERE pn.user.id = :userId AND pn.txHash = :txHash AND pn.network = :network")
  Long checkExistNote(@Param("userId") Long userId, @Param("txHash") String txHash,
      @Param("network")
      ENetworkType network);

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: get list note
   * @update:
   */
  @Query(value = "SELECT pn FROM PrivateNoteEntity pn "
      + "JOIN UserEntity ue ON pn.user.id = ue.id "
      + "WHERE ue.id = :userId AND pn.network = :network "
      + "ORDER BY pn.createdDate DESC")
  Page<PrivateNoteEntity> findAllNote(@Param("userId") Long userId, @Param("network")
  ENetworkType network, Pageable pageable);
}
