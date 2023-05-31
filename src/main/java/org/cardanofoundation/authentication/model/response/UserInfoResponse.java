package org.cardanofoundation.authentication.model.response;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoResponse {

  private String address;

  private String email;

  private String avatar;

  private Integer sizeNote;

  private Integer sizeBookmark;

  private Instant lastLogin;
}
