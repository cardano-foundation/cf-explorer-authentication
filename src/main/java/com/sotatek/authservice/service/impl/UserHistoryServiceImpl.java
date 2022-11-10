package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.UserHistoryEntity;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.repository.UserHistoryRepository;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.service.UserHistoryService;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {

  @Autowired
  private UserHistoryRepository userHistoryRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public void saveUserHistory(EUserAction action, String ipAddress, Instant actionTime,
      Boolean isSuccess, UserEntity user) {
    UserHistoryEntity authenticationHistory = new UserHistoryEntity();
    authenticationHistory.setUser(user);
    authenticationHistory.setActionTime(actionTime);
    authenticationHistory.setIpAddress(ipAddress);
    authenticationHistory.setUserAction(action);
    authenticationHistory.setIsSuccess(true);
    userHistoryRepository.save(authenticationHistory);
  }
}
