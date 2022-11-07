package com.sotatek.authservice.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponse {

  private String message;

  private String nonce;

  public SignUpResponse(String message) {
    this.message = message;
  }

  public SignUpResponse(String message, String nonce) {
    this.message = message;
    this.nonce = nonce;
  }
}
