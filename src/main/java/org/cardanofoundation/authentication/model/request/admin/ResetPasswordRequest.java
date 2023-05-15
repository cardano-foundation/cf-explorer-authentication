package org.cardanofoundation.authentication.model.request.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {

  @NotNull
  @NotBlank
  private String code;

  @NotNull
  @NotBlank
  private String password;
}
