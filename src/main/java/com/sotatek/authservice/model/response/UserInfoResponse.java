package com.sotatek.authservice.model.response;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoResponse {

  private String username;

  private String email;

  private String avatar;

  private Integer sizeNote;

  private Integer sizeBookmark;

  private Instant lastLogin;

  private String wallet;
}
