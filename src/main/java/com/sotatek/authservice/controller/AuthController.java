package com.sotatek.authservice.controller;

import com.sotatek.authservice.model.request.auth.RefreshTokenRequest;
import com.sotatek.authservice.model.request.auth.SignInRequest;
import com.sotatek.authservice.model.request.auth.SignOutRequest;
import com.sotatek.authservice.model.request.auth.SignUpRequest;
import com.sotatek.authservice.model.request.auth.TransfersWalletRequest;
import com.sotatek.authservice.model.response.RefreshTokenResponse;
import com.sotatek.authservice.model.response.SignInResponse;
import com.sotatek.authservice.model.response.SignUpResponse;
import com.sotatek.authservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "")
public class AuthController {

  private final AuthenticationService authenticationService;

  @PostMapping("/sign-in")
  public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
    return ResponseEntity.ok(authenticationService.signIn(signInRequest));
  }

  @PostMapping("/sign-up")
  public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<RefreshTokenResponse> refreshToken(
      @Valid @RequestBody RefreshTokenRequest refreshTokenRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        authenticationService.refreshToken(refreshTokenRequest, httpServletRequest));
  }

  @PostMapping("/sign-out")
  public ResponseEntity<String> signOut(@Valid @RequestBody SignOutRequest signOutRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(authenticationService.signOut(signOutRequest, httpServletRequest));
  }

  @PostMapping("/transfers-wallet")
  public ResponseEntity<SignInResponse> transfersWallet(
      @Valid @RequestBody TransfersWalletRequest transfersWalletRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        authenticationService.transfersWallet(transfersWalletRequest, httpServletRequest));
  }
}
