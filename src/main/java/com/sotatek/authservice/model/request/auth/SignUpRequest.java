package com.sotatek.authservice.model.request.auth;

import com.sotatek.authservice.model.request.base.BaseRequest;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
public class SignUpRequest extends BaseRequest {

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

  private Set<Integer> iRoles;
}
