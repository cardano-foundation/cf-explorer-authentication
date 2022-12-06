package com.sotatek.authservice.service;

import com.sotatek.authservice.model.request.user.EditUserRequest;
import com.sotatek.authservice.model.response.UserInfoResponse;
import com.sotatek.authservice.model.response.UserResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  /*
   * @author: phuc.nguyen5
   * @since: 19/10/2022
   * description: get nonce value by public address from user table
   * @update:
   */
  String findNonceByAddress(String address);

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
   * description: edit user
   * @update: 05/12/2022
   */
  UserResponse editUser(EditUserRequest editUserRequest, HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 05/11/2022
   * description: get user info
   * @update:
   */
  UserInfoResponse infoUser(HttpServletRequest httpServletRequest);
}
