package com.sotatek.authservice.service;

import com.sotatek.authservice.model.request.auth.SignInRequest;
import com.sotatek.authservice.model.request.auth.SignOutRequest;
import com.sotatek.authservice.model.request.auth.SignUpRequest;
import com.sotatek.authservice.model.request.auth.TransfersWalletRequest;
import com.sotatek.authservice.model.response.RefreshTokenResponse;
import com.sotatek.authservice.model.response.SignInResponse;
import com.sotatek.authservice.model.response.SignUpResponse;
import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

  /*
   * @author: phuc.nguyen5
   * @since: 21/10/2022
   * description: process login with signature
   * @update:
   */
  SignInResponse signIn(SignInRequest signInRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 21/10/2022
   * description: process register account with ipaddress from ada wallet
   * @update: 6/12/2022
   */
  SignUpResponse signUp(SignUpRequest signUpRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 24/10/2022
   * description: process gen new access token by refresh token
   * @update: 13/12/2022
   */
  RefreshTokenResponse refreshToken(String refreshJwt,
      HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 24/10/2022
   * description: process logout account
   * @update: 8/11/2022
   */
  String signOut(SignOutRequest signOutRequest, HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 8/11/2022
   * description: process transfers wallet in account
   * @update:
   */
  SignInResponse transfersWallet(TransfersWalletRequest transfersWalletRequest,
      HttpServletRequest httpServletRequest);
}
