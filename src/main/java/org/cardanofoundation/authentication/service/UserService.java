package org.cardanofoundation.authentication.service;

import jakarta.servlet.http.HttpServletRequest;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.enums.EStatus;
import org.cardanofoundation.authentication.model.request.EditUserRequest;
import org.cardanofoundation.authentication.model.request.auth.SignUpRequest;
import org.cardanofoundation.authentication.model.response.UserInfoResponse;
import org.cardanofoundation.authentication.model.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends UserDetailsService {

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
  UserInfoResponse infoUser(HttpServletRequest httpServletRequest, String network);

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
   * description: find user by accountId
   * @update:
   */
  UserEntity findByAccountId(String accountId);

  /*
   * @author: phuc.nguyen5
   * @since: 7/1/2023
   * description: check exist email
   * @update:
   */
  Boolean checkExistEmail(String email);

  /*
   * @author: phuc.nguyen5
   * @since: 9/1/2023
   * description: active user
   * @update:
   */
  void activeUser(String accountId);

  /*
   * @author: phuc.nguyen5
   * @since: 10/1/2023
   * description: find user by email
   * @update:
   */
  UserEntity findByEmailAndStatus(String email, EStatus status);

  /*
   * @author: phuc.nguyen5
   * @since: 16/1/2023
   * description: edit user
   * @update:
   */
  UserResponse editUser(EditUserRequest editUserRequest, HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 24/02/2023
   * description: save empty user
   * @update:
   */
  UserEntity saveUser(String address);

  /*
   * @author: phuc.nguyen5
   * @since: 29/06/2023
   * description: check exist email and status
   * @update:
   */
  Boolean checkExistEmailAndStatus(String email, EStatus status);
}
