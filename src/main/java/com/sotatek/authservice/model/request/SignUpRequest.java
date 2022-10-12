package com.sotatek.authservice.model.request;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
public class SignUpRequest {

  @NotNull
  @NotBlank
  private String username;

  @NotNull
  @NotBlank
  private String publicAddress;

  @Email
  private String email;

  @NumberFormat
  private String phone;

  private Set<Integer> roles;
}
