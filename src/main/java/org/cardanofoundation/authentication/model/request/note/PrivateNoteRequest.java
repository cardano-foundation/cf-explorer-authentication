package org.cardanofoundation.authentication.model.request.note;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.cardanofoundation.authentication.model.enums.ENetworkType;

@Getter
@Setter
public class PrivateNoteRequest {

  @NotBlank
  private String txHash;

  private String note;

  @NotBlank
  private ENetworkType network;
}
