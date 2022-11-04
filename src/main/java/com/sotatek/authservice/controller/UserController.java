package com.sotatek.authservice.controller;

import com.sotatek.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/get-nonce/{stakeAddress}")
  public ResponseEntity<String> findNonceByAddress(@PathVariable String stakeAddress) {
    return ResponseEntity.ok(userService.findNonceByAddress(stakeAddress));
  }
}
