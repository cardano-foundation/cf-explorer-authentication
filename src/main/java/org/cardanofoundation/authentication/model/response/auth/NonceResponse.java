package org.cardanofoundation.authentication.model.response.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NonceResponse {

  private String message;

  private String nonce;
}
