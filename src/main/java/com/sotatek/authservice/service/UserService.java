package com.sotatek.authservice.service;

import com.sotatek.authservice.model.request.user.DeleteUserRequest;
import com.sotatek.authservice.model.request.user.EditUserRequest;
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
   * @since: 9/11/2022
   * description: check exist username
   * @update:
   */
  Boolean checkExistUsername(String username);

  /*
   * @author: phuc.nguyen5
   * @since: 9/11/2022
   * description: check exist username
   * @update:
   */
  Boolean editUser(EditUserRequest editUserRequest, String username);

  /*
   * @author: phuc.nguyen5
   * @since: 9/11/2022
   * description: delete user
   * @update:
   */
  Boolean deleteUser(DeleteUserRequest deleteUserRequest);
}
