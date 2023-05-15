package org.cardanofoundation.authentication.repository;

import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.entity.UserHistoryEntity;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends
    JpaRepository<UserHistoryEntity, Long> {

  UserHistoryEntity findFirstByUserAndUserActionOrderByActionTimeDesc(UserEntity user,
      EUserAction userAction);
}
