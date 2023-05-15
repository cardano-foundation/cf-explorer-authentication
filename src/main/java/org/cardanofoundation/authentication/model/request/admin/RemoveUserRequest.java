package org.cardanofoundation.authentication.model.request.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class RemoveUserRequest {

  @NonNull
  @NotBlank
  private String password;
}
