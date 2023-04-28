package com.sotatek.authservice.controller;

import com.sotatek.authservice.model.request.admin.ResetPasswordRequest;
import com.sotatek.authservice.model.response.MessageResponse;
import com.sotatek.authservice.service.VerifyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class VerifyMailController {

  private final VerifyService verifyService;

  @GetMapping(value = "/active")
  public ResponseEntity<MessageResponse> checkVerifySignUpByEmail(
      @RequestParam("code") String code) {
    return ResponseEntity.ok(verifyService.checkVerifySignUpByEmail(code));
  }

  @GetMapping(value = "/forgot-password")
  public ResponseEntity<MessageResponse> resetPassword(@RequestParam("email") String email) {
    return ResponseEntity.ok(
        verifyService.forgotPassword(email));
  }

  @PutMapping(value = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageResponse> newPassword(
      @RequestBody ResetPasswordRequest resetPasswordRequest) {
    return ResponseEntity.ok(
        verifyService.resetPassword(resetPasswordRequest));
  }
}
