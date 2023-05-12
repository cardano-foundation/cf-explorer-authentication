package org.cardanofoundation.authentication.service;

import jakarta.servlet.http.HttpServletRequest;
import org.cardanofoundation.authentication.model.request.admin.RemoveUserRequest;
import org.cardanofoundation.authentication.model.request.admin.SignInAdminRequest;
import org.cardanofoundation.authentication.model.request.admin.SignUpAdminRequest;
import org.cardanofoundation.authentication.model.request.auth.SignOutRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.auth.RefreshTokenResponse;
import org.cardanofoundation.authentication.model.response.auth.SignInResponse;

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
