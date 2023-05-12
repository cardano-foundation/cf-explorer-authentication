package org.cardanofoundation.authentication.model.request.bookmark;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.cardanofoundation.authentication.model.enums.EBookMarkType;
import org.cardanofoundation.authentication.model.enums.ENetworkType;

@Getter
@Setter
public class BookMarkRequest {

  private String urlPage;

  @NotBlank
  private String keyword;

  @NotBlank
  private EBookMarkType type;

  @NotBlank
  private ENetworkType network;

}
