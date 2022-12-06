package com.sotatek.authservice.model.request.user;

import com.sotatek.authservice.model.request.base.BaseRequest;
import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserRequest extends BaseRequest {

  @Email
  private String email;

  private String avatar;
}
