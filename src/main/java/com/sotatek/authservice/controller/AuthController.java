package com.sotatek.authservice.controller;

import com.sotatek.authservice.model.enums.EWalletName;
import com.sotatek.authservice.model.request.auth.SignInRequest;
import com.sotatek.authservice.model.request.auth.SignOutRequest;
import com.sotatek.authservice.model.request.auth.SignUpRequest;
import com.sotatek.authservice.model.response.MessageResponse;
import com.sotatek.authservice.model.response.auth.NonceResponse;
import com.sotatek.authservice.model.response.auth.RefreshTokenResponse;
import com.sotatek.authservice.model.response.auth.SignInResponse;
import com.sotatek.authservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "")
public class AuthController {

  private final AuthenticationService authenticationService;

  @PostMapping(value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
    return ResponseEntity.ok(authenticationService.signIn(signInRequest));
  }

  @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
  }

  @GetMapping("/refresh-token")
  public ResponseEntity<RefreshTokenResponse> refreshToken(
      @Valid @RequestParam("refreshJwt") String refreshJwt,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        authenticationService.refreshToken(refreshJwt, httpServletRequest));
  }

  @PostMapping(value = "/sign-out", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageResponse> signOut(@Valid @RequestBody SignOutRequest signOutRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(authenticationService.signOut(signOutRequest, httpServletRequest));
  }

  @GetMapping("/get-nonce")
  public ResponseEntity<NonceResponse> findNonceByAddress(@RequestParam("address") String address,
      @RequestParam("walletName")
      EWalletName walletName) {
    return ResponseEntity.ok(authenticationService.findNonceByAddress(address, walletName));
  }
}
