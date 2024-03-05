package org.cardanofoundation.authentication.model.response;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookMarkResponse {

  private Long id;

  private String urlPage;

  private String keyword;

  private String type;

  private String network;

  private Instant createdDate;
}
