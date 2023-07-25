package org.cardanofoundation.authentication.service;

import jakarta.servlet.http.HttpServletRequest;
import org.cardanofoundation.authentication.model.request.auth.SignInRequest;
import org.cardanofoundation.authentication.model.request.auth.SignOutRequest;
import org.cardanofoundation.authentication.model.request.auth.SignUpRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.auth.NonceResponse;
import org.cardanofoundation.authentication.model.response.auth.RefreshTokenResponse;
import org.cardanofoundation.authentication.model.response.auth.SignInResponse;

public interface AuthenticationService {

  /*
   * @since: 21/10/2022
   * description: process login with wallet or email
   * @update:
   */
  SignInResponse signIn(SignInRequest signInRequest);

  /*
   * @since: 21/10/2022
   * description: process register account
   * @update: 6/12/2022
   */
  MessageResponse signUp(SignUpRequest signUpRequest);

  /*
   * @since: 24/10/2022
   * description: process gen new access token by refresh token
   * @update: 13/12/2022
   */
  RefreshTokenResponse refreshToken(String refreshJwt,
      HttpServletRequest httpServletRequest);

  /*
   * @since: 24/10/2022
   * description: process logout account
   * @update: 8/11/2022
   */
  MessageResponse signOut(SignOutRequest signOutRequest, HttpServletRequest httpServletRequest);

  /*
   * @since: 24/02/2023
   * description: get nonce value by public address from user table
   * @update:
   */
  NonceResponse findNonceByAddress(String address, String walletName);
}
