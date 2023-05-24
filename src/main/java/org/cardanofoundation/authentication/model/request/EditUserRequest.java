package org.cardanofoundation.authentication.model.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserRequest {

  @Email
  private String email;

  private String address;
}
