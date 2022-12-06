package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.PrivateNoteEntity;
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
  @Query(value = "SELECT count(pn.id) FROM PrivateNoteEntity pn WHERE pn.user.id = :userId")
  Integer getCountNoteByUser(@Param("userId") Long userId);

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: check exist note
   * @update:
   */
  @Query(value = "SELECT pn.id FROM PrivateNoteEntity pn WHERE pn.user.id = :userId AND pn.txHash = :txHash")
  Long checkExistNote(@Param("userId") Long userId, @Param("txHash") String txHash);

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: get list note
   * @update:
   */
  @Query(value = "SELECT pn FROM PrivateNoteEntity pn "
      + "JOIN UserEntity ue ON pn.user.id = ue.id "
      + "WHERE ue.username = :username AND be.type = :type")
  Page<PrivateNoteEntity> findAllNote(@Param("username") String username, Pageable pageable);
}
