package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.UserHistoryEntity;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.repository.UserHistoryRepository;
import com.sotatek.authservice.service.UserHistoryService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
