package org.cardanofoundation.authentication.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

  private String address;

  private String email;

  private String avatar;
}
