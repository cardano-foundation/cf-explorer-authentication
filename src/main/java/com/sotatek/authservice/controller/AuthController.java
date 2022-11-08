package com.sotatek.authservice.controller;

import com.sotatek.authservice.model.request.RefreshTokenRequest;
import com.sotatek.authservice.model.request.SignInRequest;
import com.sotatek.authservice.model.request.SignOutRequest;
import com.sotatek.authservice.model.request.SignUpRequest;
import com.sotatek.authservice.model.request.TransfersWalletRequest;
import com.sotatek.authservice.model.response.RefreshTokenResponse;
import com.sotatek.authservice.model.response.SignInResponse;
import com.sotatek.authservice.model.response.SignUpResponse;
import com.sotatek.authservice.service.AuthenticationService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/sign-in")
  public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
    return authenticationService.signIn(signInRequest);
  }

  @PostMapping("/sign-up")
  public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    return authenticationService.signUp(signUpRequest);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<RefreshTokenResponse> refreshToken(
      @Valid @RequestBody RefreshTokenRequest refreshTokenRequest,
      HttpServletRequest httpServletRequest) {
    return authenticationService.refreshToken(refreshTokenRequest, httpServletRequest);
  }

  @PostMapping("/sign-out")
  public ResponseEntity<String> signOut(@Valid @RequestBody SignOutRequest signOutRequest,
      HttpServletRequest httpServletRequest) {
    return authenticationService.signOut(signOutRequest, httpServletRequest);
  }

  @PostMapping("/transfers-wallet")
  public ResponseEntity<SignInResponse> transfersWallet(
      @Valid @RequestBody TransfersWalletRequest transfersWalletRequest,
      HttpServletRequest httpServletRequest) {
    return authenticationService.transfersWallet(transfersWalletRequest, httpServletRequest);
  }
}
