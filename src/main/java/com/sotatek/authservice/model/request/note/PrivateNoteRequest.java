package com.sotatek.authservice.model.request.note;

import com.sotatek.authservice.model.enums.ENetworkType;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class PrivateNoteRequest {

  @NonNull
  @NotBlank
  private String txHash;

  @NonNull
  private String note;

  @NonNull
  @NotBlank
  private ENetworkType network;
}
