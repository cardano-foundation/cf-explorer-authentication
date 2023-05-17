package org.cardanofoundation.authentication.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserRequest {

  @NotNull
  @Email
  private String email;
}
