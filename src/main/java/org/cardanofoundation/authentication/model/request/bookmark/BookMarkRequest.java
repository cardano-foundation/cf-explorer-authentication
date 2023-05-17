package org.cardanofoundation.authentication.model.request.bookmark;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.cardanofoundation.authentication.model.enums.EBookMarkType;
import org.cardanofoundation.authentication.model.enums.ENetworkType;

@Getter
@Setter
public class BookMarkRequest {

  private String urlPage;

  @NotNull
  @NotBlank
  private String keyword;

  @NotNull
  private EBookMarkType type;

  @NotNull
  private ENetworkType network;

}
