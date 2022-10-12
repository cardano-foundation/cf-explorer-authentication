package com.sotatek.authservice.service;

import com.sotatek.authservice.model.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  /*
   * @author: phuc.nguyen5
   * @since: 19/10/2022
   * description: get nonce value by public address from user table
   * @update:
   */
  String findNonceByAddress(String publicAddress);

  /*
   * @author: phuc.nguyen5
   * @since: 21/10/2022
   * description: update new nonce value in user table
   * @update:
   */
  void updateNewNonce(UserEntity user);
}
