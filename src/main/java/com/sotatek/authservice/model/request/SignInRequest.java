package com.sotatek.authservice.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest extends BaseRequest {

  @NotBlank
  @NotNull
  private String stakeAddress;

  @NotBlank
  @NotNull
  private String signature;
}
