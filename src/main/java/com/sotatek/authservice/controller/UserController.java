package com.sotatek.authservice.controller;

import com.sotatek.authservice.model.request.user.DeleteUserRequest;
import com.sotatek.authservice.model.request.user.EditUserRequest;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtProvider jwtProvider;

  @GetMapping("/get-nonce/{stakeAddress}")
  public ResponseEntity<String> findNonceByAddress(@PathVariable String stakeAddress) {
    return ResponseEntity.ok(userService.findNonceByAddress(stakeAddress));
  }

  @GetMapping("/exist-username")
  public ResponseEntity<Boolean> checkExistUsername(@RequestParam("username") String username) {
    return ResponseEntity.ok(userService.checkExistUsername(username));
  }

  @PutMapping("/edit")
  public ResponseEntity<Boolean> edit(@Valid @RequestBody EditUserRequest editUserRequest,
      HttpServletRequest httpServletRequest) {
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    return ResponseEntity.ok(userService.editUser(editUserRequest, username));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Boolean> delete(@Valid @RequestBody DeleteUserRequest deleteUserRequest) {
    return ResponseEntity.ok(userService.deleteUser(deleteUserRequest));
  }
}
