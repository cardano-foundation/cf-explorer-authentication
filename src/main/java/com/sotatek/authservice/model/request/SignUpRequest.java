package com.sotatek.authservice.model.request;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
public class SignUpRequest {

  @NotNull
  @NotBlank
  private String username;

  @Email
  private String email;

  @NumberFormat
  private String phone;

  private String avatar;

  @NotNull
  private WalletRequest wallet;

  private String ipAddress;

  private Set<Integer> roles;
}
