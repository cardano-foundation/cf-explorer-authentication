package org.cardanofoundation.authentication.model.response;

import org.cardanofoundation.authentication.model.enums.EBookMarkType;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarkResponse {

  private Long id;

  private String urlPage;

  private String keyword;

  private EBookMarkType type;

  private ENetworkType network;

  private Instant createdDate;
}
