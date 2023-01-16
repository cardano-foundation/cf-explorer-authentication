package com.sotatek.authservice.controller;

import com.sotatek.authservice.model.request.EditUserRequest;
import com.sotatek.authservice.model.response.ActivityLogResponse;
import com.sotatek.authservice.model.response.UserInfoResponse;
import com.sotatek.authservice.model.response.UserResponse;
import com.sotatek.authservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

  @PutMapping("/edit-avatar")
  public ResponseEntity<UserResponse> edit(@RequestParam("avatar") MultipartFile avatar,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(userService.editAvatar(avatar, httpServletRequest));
  }

  @PutMapping("/edit")
  public ResponseEntity<UserResponse> edit(@RequestBody EditUserRequest editUserRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(userService.editUser(editUserRequest, httpServletRequest));
  }

  @GetMapping("/info")
  public ResponseEntity<UserInfoResponse> info(HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(userService.infoUser(httpServletRequest));
  }

  @GetMapping("/activities-log")
  public ResponseEntity<List<ActivityLogResponse>> getLog(HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(userService.getLog(httpServletRequest));
  }

  @GetMapping("/exist-email")
  public ResponseEntity<Boolean> checkExistEmail(@RequestParam("email") String email) {
    return ResponseEntity.ok(userService.checkExistEmail(email));
  }
}
