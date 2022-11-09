package com.sotatek.authservice.model.request.user;

import com.sotatek.authservice.model.request.base.BaseRequest;
import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
public class EditUserRequest extends BaseRequest {

  private String username;

  @Email
  private String email;

  @NumberFormat
  private String phone;

  private String avatar;
}
