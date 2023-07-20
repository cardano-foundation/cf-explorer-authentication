package org.cardanofoundation.authentication.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cardanofoundation.explorer.common.exceptions.enums.ErrorCode;

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