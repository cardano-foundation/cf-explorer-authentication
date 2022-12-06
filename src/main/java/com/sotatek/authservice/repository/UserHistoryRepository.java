package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.UserHistoryEntity;
import com.sotatek.authservice.model.enums.EUserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends
    JpaRepository<UserHistoryEntity, Long> {

  UserHistoryEntity findFirstByUserAndUserActionOrderByActionTimeDesc(UserEntity user,
      EUserAction userAction);
}
