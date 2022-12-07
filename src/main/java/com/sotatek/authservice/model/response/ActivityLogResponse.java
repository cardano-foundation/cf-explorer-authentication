package com.sotatek.authservice.model.response;

import com.sotatek.authservice.model.enums.EUserAction;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActivityLogResponse {

  private EUserAction userAction;

  private String ipAddress;

  private Instant actionTime;

  private String description;

  private String strAction;
}
