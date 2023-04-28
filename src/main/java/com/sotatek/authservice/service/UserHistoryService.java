package com.sotatek.authservice.service;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.EUserAction;
import java.time.Instant;

public interface UserHistoryService {

  /*
   * @author: phuc.nguyen5
   * @since: 20/10/2022
   * description: save user history after login, logout
   * @update: 2/1/2023
   */
  void saveUserHistory(EUserAction action, Instant actionTime, UserEntity user);
}
