package org.cardanofoundation.authentication.model.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
  private String signature;

  private String key;

  @Email private String email;

  private String password;

  @NotNull
  @Max(1)
  @Min(0)
  private Integer type;
}
