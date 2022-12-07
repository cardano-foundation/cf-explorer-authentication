package com.sotatek.authservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/csrf")
@Tag(name = "Csrf Controller", description = "")
public class CsrfController {

  @GetMapping("")
  public ResponseEntity<CsrfToken> csrf(CsrfToken token) {
    return ResponseEntity.ok(token);
  }
}
