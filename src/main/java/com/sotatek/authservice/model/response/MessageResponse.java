package com.sotatek.authservice.model.response;

import com.sotatek.cardanocommonapi.exceptions.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {

  private String code;

  private String message;

  public MessageResponse(ErrorCode err) {
    this.code = err.getServiceErrorCode();
    this.message = err.getDesc();
  }
}
