package com.sotatek.authservice.model.request.admin;

import com.sotatek.authservice.model.enums.ERole;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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
