package org.cardanofoundation.authentication.service;

import java.time.Instant;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.enums.EUserAction;

public interface UserHistoryService {

  /*
   * @since: 20/10/2022
   * description: save user history after login, logout
   * @update: 2/1/2023
   */
  void saveUserHistory(EUserAction action, Instant actionTime, UserEntity user);
}
