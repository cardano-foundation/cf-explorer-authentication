package org.cardanofoundation.authentication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.cardanofoundation.authentication.model.request.auth.ResetPasswordRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.service.VerifyService;
import org.cardanofoundation.explorer.common.exceptions.ErrorResponse;
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
@Tag(name = "Verify Mail Controller")
@Validated
public class VerifyMailController {

  private final VerifyService verifyService;

  @Operation(description = "Activate the registered account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping(value = "/active")
  public ResponseEntity<MessageResponse> checkVerifySignUpByEmail(
      @Parameter(
          name = "code",
          description = "Account activation code",
          required = true)
      @Valid @RequestParam("code") String code) {
    return ResponseEntity.ok(verifyService.checkVerifySignUpByEmail(code));
  }

  @Operation(description = "Initiate the password reset process")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping(value = "/forgot-password")
  public ResponseEntity<MessageResponse> resetPassword(
      @Parameter(
          name = "email",
          description = "Email address used to create the account",
          example = "phuc.viet@gmail.com",
          required = true)
      @Valid @RequestParam("email") @Email String email) {
    return ResponseEntity.ok(
        verifyService.forgotPassword(email));
  }

  @Operation(description = "Set a new password for the account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping(value = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageResponse> newPassword(@Valid
  @RequestBody ResetPasswordRequest resetPasswordRequest) {
    return ResponseEntity.ok(
        verifyService.resetPassword(resetPasswordRequest));
  }

  @Operation(description = "Check the validity of the authentication code")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Boolean.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping(value = "/expired-code")
  public ResponseEntity<Boolean> checkExpiredCode(
      @Parameter(
          name = "code",
          description = "Account verify code",
          required = true)
      @Valid @RequestParam("code") String code) {
    return ResponseEntity.ok(verifyService.checkExpiredCode(code));
  }
}
