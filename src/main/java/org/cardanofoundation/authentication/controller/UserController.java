package org.cardanofoundation.authentication.controller;

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
@Tag(name = "User Controller", description = "")
@Validated
public class UserController {

  private final UserService userService;

  @PutMapping("/edit-avatar")
  public ResponseEntity<UserResponse> editAvatar(@RequestParam("avatar") MultipartFile avatar,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(userService.editAvatar(avatar, httpServletRequest));
  }

  @PutMapping("/edit")
  public ResponseEntity<UserResponse> edit(@Valid @RequestBody EditUserRequest editUserRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(userService.editUser(editUserRequest, httpServletRequest));
  }

  @GetMapping("/info")
  public ResponseEntity<UserInfoResponse> info(
      @RequestParam("network") @EnumValid(enumClass = ENetworkType.class) String network,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(userService.infoUser(httpServletRequest, network));
  }

  @GetMapping("/exist-email")
  public ResponseEntity<Boolean> checkExistEmail(@RequestParam("email") @Email String email) {
    return ResponseEntity.ok(userService.checkExistEmail(email));
  }
}
