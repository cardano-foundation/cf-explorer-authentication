package com.sotatek.authservice.service;

import com.sotatek.authservice.model.request.admin.RemoveUserRequest;
import com.sotatek.authservice.model.request.admin.ResetPasswordRequest;
import com.sotatek.authservice.model.request.admin.SignInAdminRequest;
import com.sotatek.authservice.model.request.admin.SignUpAdminRequest;
import com.sotatek.authservice.model.request.auth.SignOutRequest;
import com.sotatek.authservice.model.response.MessageResponse;
import com.sotatek.authservice.model.response.auth.RefreshTokenResponse;
import com.sotatek.authservice.model.response.auth.SignInResponse;
import javax.servlet.http.HttpServletRequest;

public interface AuthenticationAdminService {

  /*
   * @author: phuc.nguyen5
   * @since: 09/01/2023
   * description: process sign up admin account
   * @update:
   */
  MessageResponse signUp(SignUpAdminRequest signUpAdmin);

  /*
   * @author: phuc.nguyen5
   * @since: 10/01/2023
   * description: process sign in admin account
   * @update:
   */
  SignInResponse signIn(SignInAdminRequest signInAdmin);

  /*
   * @author: phuc.nguyen5
   * @since: 11/01/2023
   * description: process gen new access token by refresh token
   * @update:
   */
  RefreshTokenResponse refreshToken(String refreshJwt,
      HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 12/1/2023
   * description: process logout account
   * @update:
   */
  MessageResponse signOut(SignOutRequest signOutRequest, HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 12/1/2023
   * description: process remove admin account
   * @update:
   */
  MessageResponse remove(RemoveUserRequest removeUserRequest,
      HttpServletRequest httpServletRequest);
}
