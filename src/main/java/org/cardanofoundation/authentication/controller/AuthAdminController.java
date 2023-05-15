package org.cardanofoundation.authentication.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.cardanofoundation.authentication.model.request.admin.RemoveUserRequest;
import org.cardanofoundation.authentication.model.request.admin.SignInAdminRequest;
import org.cardanofoundation.authentication.model.request.admin.SignUpAdminRequest;
import org.cardanofoundation.authentication.model.request.auth.SignOutRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.auth.RefreshTokenResponse;
import org.cardanofoundation.authentication.model.response.auth.SignInResponse;
import org.cardanofoundation.authentication.service.AuthenticationAdminService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Authentication Admin Controller", description = "")
public class AuthAdminController {

  private final AuthenticationAdminService authenticationAdminService;

  @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageResponse> signUp(
      @Valid @RequestBody SignUpAdminRequest signUpAdmin) {
    return ResponseEntity.ok(authenticationAdminService.signUp(signUpAdmin));
  }

  @PostMapping(value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInAdminRequest signInAdmin) {
    return ResponseEntity.ok(authenticationAdminService.signIn(signInAdmin));
  }

  @GetMapping("/refresh-token")
  public ResponseEntity<RefreshTokenResponse> refreshToken(
      @Valid @RequestParam("refreshJwt") String refreshJwt, HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        authenticationAdminService.refreshToken(refreshJwt, httpServletRequest));
  }

  @PostMapping(value = "/sign-out", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageResponse> signOut(@Valid @RequestBody SignOutRequest signOutRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        authenticationAdminService.signOut(signOutRequest, httpServletRequest));
  }

  @DeleteMapping(value = "/remove")
  public ResponseEntity<MessageResponse> remove(@RequestBody RemoveUserRequest removeUserRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        authenticationAdminService.remove(removeUserRequest, httpServletRequest));
  }
}