package com.sotatek.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/csrf")
public class CsrfController {

  @GetMapping("")
  public ResponseEntity<CsrfToken> csrf(CsrfToken token) {
    return ResponseEntity.ok(token);
  }
}
