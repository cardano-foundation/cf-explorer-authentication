package com.sotatek.authservice.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseRequest {

  private String ipAddress;
}
