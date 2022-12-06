package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.PrivateNoteEntity;
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
}
