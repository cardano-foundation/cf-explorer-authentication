package org.cardanofoundation.authentication.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.cardanofoundation.authentication.model.request.event.EventModel;
import org.cardanofoundation.authentication.model.response.UserInfoResponse;
import org.cardanofoundation.authentication.service.KeycloakService;
import org.cardanofoundation.explorer.common.exception.ErrorResponse;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Controller")
@Validated
@Log4j2
public class UserController {

  private final KeycloakService keycloakService;

  @Value("${secret-code}")
  private String secretCode;

  @Operation(description = "Get account information")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(schema = @Schema(implementation = UserInfoResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input parameter error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Authentication error unsuccessful",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error not specified",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping("/info")
  public ResponseEntity<UserInfoResponse> info(HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(keycloakService.infoUser(httpServletRequest));
  }

  @Operation(description = "Check the existence of an email")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(schema = @Schema(implementation = Boolean.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input parameter error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Authentication error unsuccessful",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error not specified",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping("/exist-email")
  public ResponseEntity<Boolean> checkExistEmail(
      @Parameter(
              name = "email",
              description = "Email address",
              example = "phuc.viet@gmail.com",
              required = true)
          @RequestParam("email")
          @Email
          String email) {
    return ResponseEntity.ok(keycloakService.checkExistEmail(email));
  }

  @PostMapping("/role-mapping")
  public ResponseEntity<Boolean> roleMapping(@RequestBody EventModel model) {
    if (!model.getSecretCode().equals(secretCode)) {
      log.warn(
          "Secret code is not correct! setup `{}`, received `{}` !",
          secretCode,
          model.getSecretCode());
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(keycloakService.roleMapping(model), HttpStatus.CREATED);
  }
}
