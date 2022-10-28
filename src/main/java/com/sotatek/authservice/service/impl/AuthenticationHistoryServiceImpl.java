package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.model.entity.AuthenticationHistoryEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.repository.AuthenticationHistoryRepository;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.service.AuthenticationHistoryService;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationHistoryServiceImpl implements AuthenticationHistoryService {

  @Autowired
  private AuthenticationHistoryRepository authenticationHistoryRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public void saveAuthenticationHistory(EUserAction action, String ipAddress, Instant actionTime,
      Boolean isSuccess, String username) {
    AuthenticationHistoryEntity authenticationHistory = new AuthenticationHistoryEntity();
    Optional<UserEntity> userOpt = userRepository.findByUsername(username);
    if (userOpt.isEmpty()) {
      throw BusinessException.builder()
          .errorCode(CommonErrorCode.USER_IS_NOT_EXIST.getServiceErrorCode())
          .errorMsg(CommonErrorCode.USER_IS_NOT_EXIST.getDesc()).build();
    }
    authenticationHistory.setUser(userOpt.get());
    authenticationHistory.setActionTime(actionTime);
    authenticationHistory.setIpAddress(ipAddress);
    authenticationHistory.setUserAction(action);
    authenticationHistory.setIsSuccess(true);
    authenticationHistoryRepository.save(authenticationHistory);
  }
}
