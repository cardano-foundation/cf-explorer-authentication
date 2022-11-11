package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.BookMarkEntity;
import com.sotatek.authservice.model.enums.EBookMarkType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMarkEntity, Long> {

  @Query("SELECT be FROM BookMarkEntity be INNER JOIN UserEntity ue ON be.user.id = ue.id WHERE ue.isDeleted = false AND ue.username = :username")
  List<BookMarkEntity> findAllBookMarkByUsername(@Param("username") String username);

  @Query("SELECT be FROM BookMarkEntity be INNER JOIN UserEntity ue ON be.user.id = ue.id WHERE ue.isDeleted = false AND ue.username = :username AND be.type = :type")
  List<BookMarkEntity> findAllBookMarkByUsernameAndType(@Param("username") String username, @Param("type") EBookMarkType type);
}
