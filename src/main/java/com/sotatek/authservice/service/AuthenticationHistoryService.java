package com.sotatek.authservice.service;

import com.sotatek.authservice.model.enums.EUserAction;
import java.time.Instant;

public interface AuthenticationHistoryService {

  /*
   * @author: phuc.nguyen5
   * @since: 20/10/2022
   * description: save authentication history after login, logout
   * @update:
   */
  void saveAuthenticationHistory(EUserAction action, String ipAddress, Instant actionTime, Boolean isSuccess, String username);
}
