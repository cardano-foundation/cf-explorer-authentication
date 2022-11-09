package com.sotatek.authservice.model.request.user;

import com.sotatek.authservice.model.request.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserRequest extends BaseRequest {

  private String username;
}
