package org.cardanofoundation.authentication.model.request.note;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.explorer.common.annotation.EnumValid;
import org.cardanofoundation.explorer.common.validation.length.LengthValid;

@Getter
@Setter
public class PrivateNoteRequest {

  @NotNull
  @NotBlank
  @LengthValid(CommonConstant.TX_HASH_LENGTH)
  private String txHash;

  @NotNull
  private String note;

  @NotNull
  @EnumValid(enumClass = ENetworkType.class)
  private String network;
}
