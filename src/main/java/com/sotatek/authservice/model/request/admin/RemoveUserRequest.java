package com.sotatek.authservice.model.request.admin;

import javax.validation.constraints.NotBlank;
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
