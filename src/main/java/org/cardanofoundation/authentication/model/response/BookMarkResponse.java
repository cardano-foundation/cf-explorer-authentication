package org.cardanofoundation.authentication.model.response;

import lombok.Builder;
import org.cardanofoundation.authentication.model.enums.EBookMarkType;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import java.time.Instant;
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
