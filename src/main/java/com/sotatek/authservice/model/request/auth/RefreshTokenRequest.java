package com.sotatek.authservice.model.request.auth;

import com.sotatek.authservice.model.request.base.BaseRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest extends BaseRequest {

  @NotBlank
  @NotNull
  private String refreshToken;
}
