package org.cardanofoundation.authentication.model.request.note;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.cardanofoundation.authentication.model.enums.ENetworkType;

@Getter
@Setter
public class PrivateNoteRequest {

  @NotNull
  @NotBlank
  private String txHash;

  @NotNull
  private String note;

  @NotNull
  private ENetworkType network;
}
