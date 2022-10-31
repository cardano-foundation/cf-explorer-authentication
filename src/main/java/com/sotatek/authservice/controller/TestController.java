package com.sotatek.authservice.controller;

import com.sotatek.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {

  @Autowired
  private UserService userService;

  @GetMapping("/test")
  public String test() {
    return "test";
  }

  @PostMapping("/test-1")
  public String test1() {
    return "test";
  }
}
