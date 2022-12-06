package com.sotatek.authservice.model.request.note;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrivateNoteRequest {

  private String txHash;

  private String note;
}
