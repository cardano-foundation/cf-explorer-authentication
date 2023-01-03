package com.sotatek.authservice.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpResponse {

  private String message;

  private String nonce;
}
