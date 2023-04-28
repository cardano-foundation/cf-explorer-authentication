package com.sotatek.authservice.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

  private String username;

  private String email;

  private String avatar;
}
