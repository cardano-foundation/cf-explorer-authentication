package com.sotatek.authservice.model.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {

  private String address;

  private String signature;

  private String username;

  private String password;

  private Integer type;
}
