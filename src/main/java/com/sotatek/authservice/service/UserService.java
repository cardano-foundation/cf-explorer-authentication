package com.sotatek.authservice.service;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.EStatus;
import com.sotatek.authservice.model.request.EditUserRequest;
import com.sotatek.authservice.model.request.admin.SignUpAdminRequest;
import com.sotatek.authservice.model.request.auth.SignUpRequest;
import com.sotatek.authservice.model.response.ActivityLogResponse;
import com.sotatek.authservice.model.response.UserInfoResponse;
import com.sotatek.authservice.model.response.UserResponse;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

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
  UserResponse editAvatar(MultipartFile avatar, HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 06/12/2022
   * description: get user info
   * @update:
   */
  UserInfoResponse infoUser(HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 07/12/2022
   * description: get log activity user
   * @update:
   */
  List<ActivityLogResponse> getLog(HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 22/12/2022
   * description: save user
   * @update:
   */
  UserEntity saveUser(SignUpRequest signUpRequest);


  /*
   * @author: phuc.nguyen5
   * @since: 22/12/2022
   * description: find user by stake address wallet
   * @update:
   */
  UserEntity findUserByWalletAddress(String address);

  /*
   * @author: phuc.nguyen5
   * @since: 22/12/2022
   * description: find user by username
   * @update:
   */
  UserEntity findByUsername(String username);

  /*
   * @author: phuc.nguyen5
   * @since: 7/1/2023
   * description: check exist email
   * @update:
   */
  Boolean checkExistEmail(String email);

  /*
   * @author: phuc.nguyen5
   * @since: 7/1/2023
   * description: save user admin
   * @update:
   */
  UserEntity saveUserAdmin(SignUpAdminRequest signUpAdmin);

  /*
   * @author: phuc.nguyen5
   * @since: 9/1/2023
   * description: active user admin
   * @update:
   */
  UserEntity activeUserAdmin(String username);

  /*
   * @author: phuc.nguyen5
   * @since: 10/1/2023
   * description: find user by email
   * @update:
   */
  UserEntity findByEmailAndStatus(String email, EStatus status);

  /*
   * @author: phuc.nguyen5
   * @since: 22/12/2022
   * description: find user by username
   * @update:
   */
  UserEntity findByUsernameAndStatus(String username, EStatus status);

  /*
   * @author: phuc.nguyen5
   * @since: 16/1/2023
   * description: edit user
   * @update:
   */
  UserResponse editUser(EditUserRequest editUserRequest, HttpServletRequest httpServletRequest);

}
