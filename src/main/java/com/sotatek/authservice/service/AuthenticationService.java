package com.sotatek.authservice.service;

import com.sotatek.authservice.model.request.auth.RefreshTokenRequest;
import com.sotatek.authservice.model.request.auth.SignInRequest;
import com.sotatek.authservice.model.request.auth.SignOutRequest;
import com.sotatek.authservice.model.request.auth.SignUpRequest;
import com.sotatek.authservice.model.request.auth.TransfersWalletRequest;
import com.sotatek.authservice.model.response.RefreshTokenResponse;
import com.sotatek.authservice.model.response.SignInResponse;
import com.sotatek.authservice.model.response.SignUpResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

  /*
   * @author: phuc.nguyen5
   * @since: 20/10/2022
   * description: Add access token + username to redis after logout or refresh token
   * @update:
   */
  void blacklistJwt(String token, String username);

  /*
   * @author: phuc.nguyen5
   * @since: 20/10/2022
   * description: Check exist access token + username from redis
   * @update:
   */
  boolean isTokenBlacklisted(String token);

  /*
   * @author: phuc.nguyen5
   * @since: 21/10/2022
   * description: process login with signature
   * @update:
   */
  ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 21/10/2022
   * description: process register account with ipaddress from ada wallet
   * @update:
   */
  ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 24/10/2022
   * description: process gen new access token by refresh token
   * @update:
   */
  ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest,
      HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 24/10/2022
   * description: process logout account
   * @update: 8/11/2022
   */
  ResponseEntity<String> signOut(SignOutRequest signOutRequest,
      HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 8/11/2022
   * description: process transfers wallet in account
   * @update:
   */
  ResponseEntity<SignInResponse> transfersWallet(TransfersWalletRequest transfersWalletRequest,
      HttpServletRequest httpServletRequest);
}
