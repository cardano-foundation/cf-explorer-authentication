package org.cardanofoundation.authentication.service.impl;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.entity.UserHistoryEntity;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.repository.UserHistoryRepository;
import org.cardanofoundation.authentication.service.UserHistoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserHistoryServiceImpl implements UserHistoryService {

  private final UserHistoryRepository userHistoryRepository;

  @Override
  public void saveUserHistory(EUserAction action, Instant actionTime, UserEntity user) {
    UserHistoryEntity userHistory = UserHistoryEntity.builder().user(user).actionTime(actionTime)
        .userAction(action).build();
    userHistoryRepository.save(userHistory);
  }
}
