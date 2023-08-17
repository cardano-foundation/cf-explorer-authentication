package org.cardanofoundation.authentication.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.cardanofoundation.authentication.model.request.auth.ResetPasswordRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.service.VerifyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/verify")
@RequiredArgsConstructor
@Tag(name = "Verify Mail Controller", description = "")
@Validated
public class VerifyMailController {

  private final VerifyService verifyService;

  @GetMapping(value = "/active")
  public ResponseEntity<MessageResponse> checkVerifySignUpByEmail(
      @Valid @RequestParam("code") String code) {
    return ResponseEntity.ok(verifyService.checkVerifySignUpByEmail(code));
  }

  @GetMapping(value = "/forgot-password")
  public ResponseEntity<MessageResponse> resetPassword(
      @Valid @RequestParam("email") @Email String email, HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        verifyService.forgotPassword(email, httpServletRequest));
  }

  @PutMapping(value = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageResponse> newPassword(@Valid
  @RequestBody ResetPasswordRequest resetPasswordRequest) {
    return ResponseEntity.ok(
        verifyService.resetPassword(resetPasswordRequest));
  }

  @GetMapping(value = "/expired-code")
  public ResponseEntity<Boolean> checkExpiredCode(
      @Valid @RequestParam("code") String code) {
    return ResponseEntity.ok(verifyService.checkExpiredCode(code));
  }
}
