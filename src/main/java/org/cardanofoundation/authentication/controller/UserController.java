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
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.EditUserRequest;
import org.cardanofoundation.authentication.model.response.UserInfoResponse;
import org.cardanofoundation.authentication.model.response.UserResponse;
import org.cardanofoundation.authentication.service.UserService;
import org.cardanofoundation.explorer.common.annotation.EnumValid;
import org.cardanofoundation.explorer.common.exceptions.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Controller")
@Validated
public class UserController {

  private final UserService userService;

  @Operation(description = "Edit the profile image for the account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UserResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Authentication error unsuccessful", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping("/edit-avatar")
  public ResponseEntity<UserResponse> editAvatar(
      @Parameter(
          name = "avatar",
          description = "Image data(byte[])",
          required = true)
      @RequestParam("avatar") MultipartFile avatar,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(userService.editAvatar(avatar, httpServletRequest));
  }

  @Operation(description = "Edit account information")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UserResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Authentication error unsuccessful", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping("/edit")
  public ResponseEntity<UserResponse> edit(@Valid @RequestBody EditUserRequest editUserRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(userService.editUser(editUserRequest, httpServletRequest));
  }

  @Operation(description = "Get account information")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UserInfoResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Authentication error unsuccessful", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/info")
  public ResponseEntity<UserInfoResponse> info(
      @Parameter(
          name = "network",
          description = "Network type",
          example = "MAIN_NET",
          required = true)
      @RequestParam("network") @EnumValid(enumClass = ENetworkType.class) String network,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(userService.infoUser(httpServletRequest, network));
  }

  @Operation(description = "Check the existence of an email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Boolean.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Authentication error unsuccessful", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/exist-email")
  public ResponseEntity<Boolean> checkExistEmail(
      @Parameter(
          name = "email",
          description = "Email address",
          example = "phuc.viet@gmail.com",
          required = true)
      @RequestParam("email") @Email String email) {
    return ResponseEntity.ok(userService.checkExistEmail(email));
  }
}
