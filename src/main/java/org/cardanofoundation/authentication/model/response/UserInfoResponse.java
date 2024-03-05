package org.cardanofoundation.authentication.model.response;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoResponse {

  private String username;

  private Instant lastLogin;
}
