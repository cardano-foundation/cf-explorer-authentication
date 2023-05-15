package org.cardanofoundation.authentication.model.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignOutRequest {

  @NotNull
  @NotBlank
  private String refreshJwt;

  @NotNull
  @NotBlank
  private String username;
}
