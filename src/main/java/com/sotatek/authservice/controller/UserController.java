package com.sotatek.authservice.controller;

import com.sotatek.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/get-nonce/{publicAddress}")
  public ResponseEntity<String> findNonceByAddress(@PathVariable String publicAddress) {
    return ResponseEntity.ok(userService.findNonceByAddress(publicAddress));
  }
}
