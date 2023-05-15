package org.cardanofoundation.authentication.model.response;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrivateNoteResponse {

  private Long id;

  private String txHash;

  private String note;

  private Instant createdDate;
}
