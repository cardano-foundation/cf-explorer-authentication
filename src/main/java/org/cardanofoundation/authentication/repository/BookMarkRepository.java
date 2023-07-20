package org.cardanofoundation.authentication.repository;

import java.util.List;
import org.cardanofoundation.authentication.model.entity.BookMarkEntity;
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
   * description: find all bookmark by userId and type
   * @update: 05/12/2022
   */
  @Query(value = "SELECT be FROM BookMarkEntity be "
      + "JOIN UserEntity ue ON be.user.id = ue.id "
      + "WHERE ue.id = :userId AND be.type = :type AND be.network = :network "
      + "ORDER BY be.createdDate DESC")
  Page<BookMarkEntity> findAllBookMarkByUserAndType(@Param("userId") Long userId,
      @Param("type") String type, @Param("network") String network, Pageable pageable);

  /*
   * @author: phuc.nguyen5
   * @since: 05/12/2022
   * description: get count bookmark
   * @update:
   */
  @Query(value = "SELECT count(bm.id) FROM BookMarkEntity bm WHERE bm.user.id = :userId AND bm.network = :network")
  Integer getCountBookMarkByUser(@Param("userId") Long userId,
      @Param("network") String network);

  @Query(value = "SELECT bm.id FROM BookMarkEntity bm "
      + "WHERE bm.user.id = :userId AND bm.keyword = :keyword AND bm.type = :type AND bm.network = :network")
  Long checkExistBookMark(@Param("userId") Long userId, @Param("keyword") String keyword,
      @Param("type") String type, @Param("network") String network);

  /*
   * @author: phuc.nguyen5
   * @since: 30/01/2023
   * description: find all bookmark key by userId
   * @update:
   */
  @Query(value = "SELECT be FROM BookMarkEntity be "
      + "JOIN UserEntity ue ON be.user.id = ue.id "
      + "WHERE ue.id = :userId AND be.network = :network")
  List<BookMarkEntity> findAllKeyBookMarkByUser(@Param("userId") Long userId,
      @Param("network") String network);
}