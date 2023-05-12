package org.cardanofoundation.authentication.model.request.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.cardanofoundation.authentication.model.enums.ERole;

@Getter
@Setter
public class SignUpAdminRequest {

  @NonNull
  @NotBlank
  private String username;

  @NotNull
  @NotBlank
  @Email
  private String email;

  @NonNull
  @NotBlank
  private String password;

  @NonNull
  private ERole role;
}
