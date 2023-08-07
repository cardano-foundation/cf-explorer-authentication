package org.cardanofoundation.authentication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.request.auth.SignInRequest;
import org.cardanofoundation.authentication.model.request.auth.SignOutRequest;
import org.cardanofoundation.authentication.model.request.auth.SignUpRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.auth.NonceResponse;
import org.cardanofoundation.authentication.model.response.auth.RefreshTokenResponse;
import org.cardanofoundation.authentication.model.response.auth.SignInResponse;
import org.cardanofoundation.authentication.service.AuthenticationService;
import org.cardanofoundation.explorer.common.exceptions.ErrorResponse;
import org.cardanofoundation.explorer.common.validation.prefixed.PrefixedValid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller")
@Validated
public class AuthController {

  private final AuthenticationService authenticationService;

  @Operation(description = "Log in to the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = SignInResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping(value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SignInResponse> signIn(
      @Valid @RequestBody SignInRequest signInRequest) {
    return ResponseEntity.ok(authenticationService.signIn(signInRequest));
  }

  @Operation(description = "Sign up for an account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
  }

  @Operation(description = "Generate a new JSON Web Token (JWT) using the refresh token")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = RefreshTokenResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/refresh-token")
  public ResponseEntity<RefreshTokenResponse> refreshToken(
      @Parameter(
          name = "refreshJwt",
          description = "Refresh Token ID",
          example = "6e69d1a3-1416-4b5c-9304-d80e80d73839",
          required = true)
      @Valid @RequestParam("refreshJwt") String refreshJwt,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        authenticationService.refreshToken(refreshJwt, httpServletRequest));
  }

  @Operation(description = "Log out from the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping(value = "/sign-out", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageResponse> signOut(@Valid @RequestBody SignOutRequest signOutRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(authenticationService.signOut(signOutRequest, httpServletRequest));
  }

  @Operation(description = "Get a wallet code to generate a signature for logging into the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = NonceResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/get-nonce")
  public ResponseEntity<NonceResponse> findNonceByAddress(
      @Parameter(
          name = "address",
          description = "Wallet address",
          example = "stake1u80n7nvm3qlss9ls0krp5xh7sqxlazp8kz6n3fp5sgnul5cnxyg4p",
          required = true)
      @RequestParam("address") @PrefixedValid(CommonConstant.PREFIXED_ADDRESS) String address,
      @Parameter(
          name = "walletName",
          description = "Wallet name",
          example = "NAMI",
          required = true)
      @RequestParam("walletName") String walletName) {
    return ResponseEntity.ok(authenticationService.findNonceByAddress(address, walletName));
  }
}
