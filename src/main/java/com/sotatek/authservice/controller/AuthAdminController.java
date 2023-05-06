package com.sotatek.authservice.controller;

import com.sotatek.authservice.model.request.admin.RemoveUserRequest;
import com.sotatek.authservice.model.request.admin.SignInAdminRequest;
import com.sotatek.authservice.model.request.admin.SignUpAdminRequest;
import com.sotatek.authservice.model.request.auth.SignOutRequest;
import com.sotatek.authservice.model.response.MessageResponse;
import com.sotatek.authservice.model.response.auth.RefreshTokenResponse;
import com.sotatek.authservice.model.response.auth.SignInResponse;
import com.sotatek.authservice.service.AuthenticationAdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
