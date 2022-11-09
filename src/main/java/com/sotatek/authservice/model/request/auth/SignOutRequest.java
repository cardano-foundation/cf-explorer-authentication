package com.sotatek.authservice.model.request.auth;

import com.sotatek.authservice.model.request.base.BaseRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignOutRequest extends BaseRequest {

  @NotNull
  @NotBlank
  private String refreshToken;

  @NotNull
  @NotBlank
  private String username;
}
