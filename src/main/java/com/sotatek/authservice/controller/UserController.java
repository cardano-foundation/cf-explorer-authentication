package com.sotatek.authservice.controller;

import com.sotatek.authservice.model.request.user.EditUserRequest;
import com.sotatek.authservice.model.response.UserInfoResponse;
import com.sotatek.authservice.model.response.UserResponse;
import com.sotatek.authservice.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/get-nonce")
  public ResponseEntity<String> findNonceByAddress(@RequestParam("address") String address) {
    return ResponseEntity.ok(userService.findNonceByAddress(address));
  }

  @GetMapping("/exist-username")
  public ResponseEntity<Boolean> checkExistUsername(@RequestParam("username") String username) {
    return ResponseEntity.ok(userService.checkExistUsername(username));
  }

  @PutMapping("/edit")
  public ResponseEntity<UserResponse> edit(@Valid @RequestBody EditUserRequest editUserRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(userService.editUser(editUserRequest, httpServletRequest));
  }

  @GetMapping("/info")
  public ResponseEntity<UserInfoResponse> info(HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(userService.infoUser(httpServletRequest));
  }
}
