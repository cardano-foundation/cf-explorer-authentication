package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.BookMarkEntity;
import com.sotatek.authservice.model.enums.EBookMarkType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMarkEntity, Long> {

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: find all bookmark by username and type with deleted is false
   * @update: 05/12/2022
   */
  @Query(value = "SELECT be FROM BookMarkEntity be "
      + "JOIN UserEntity ue ON be.user.id = ue.id "
      + "WHERE ue.username = :username AND be.type = :type")
  Page<BookMarkEntity> findAllBookMarkByUserAndType(@Param("username") String username,
      @Param("type") EBookMarkType type, Pageable pageable);

  /*
   * @author: phuc.nguyen5
   * @since: 05/12/2022
   * description: get count bookmark
   * @update:
   */
  @Query(value = "SELECT count(bm.id) FROM BookMarkEntity bm WHERE bm.user.id = :userId")
  Integer getCountBookMarkByUser(@Param("userId") Long userId);

  @Query(value = "SELECT bm.id FROM BookMarkEntity bm "
      + "WHERE bm.user.id = :userId AND bm.keyword = :keyword AND bm.type = :type")
  Long checkExistBookMark(@Param("userId") Long userId, @Param("keyword") String keyword,
      @Param("type") EBookMarkType type);
}
