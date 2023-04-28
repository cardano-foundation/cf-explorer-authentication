package com.sotatek.authservice.service;

import com.sotatek.authservice.model.enums.EWalletName;
import com.sotatek.authservice.model.request.auth.SignInRequest;
import com.sotatek.authservice.model.request.auth.SignOutRequest;
import com.sotatek.authservice.model.request.auth.SignUpRequest;
import com.sotatek.authservice.model.response.MessageResponse;
import com.sotatek.authservice.model.response.auth.NonceResponse;
import com.sotatek.authservice.model.response.auth.RefreshTokenResponse;
import com.sotatek.authservice.model.response.auth.SignInResponse;
import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

  /*
   * @author: phuc.nguyen5
   * @since: 21/10/2022
   * description: process login with wallet or username
   * @update:
   */
  SignInResponse signIn(SignInRequest signInRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 21/10/2022
   * description: process register account
   * @update: 6/12/2022
   */
  MessageResponse signUp(SignUpRequest signUpRequest);

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
  MessageResponse signOut(SignOutRequest signOutRequest, HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 24/02/2023
   * description: get nonce value by public address from user table
   * @update:
   */
  NonceResponse findNonceByAddress(String address, EWalletName walletName);
}
