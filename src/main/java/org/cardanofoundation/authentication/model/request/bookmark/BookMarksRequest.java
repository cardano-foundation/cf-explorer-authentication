package org.cardanofoundation.authentication.model.request.bookmark;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.explorer.common.annotation.EnumValid;

@Getter
@Setter
public class BookMarksRequest {

  @NotNull
  private List<BookMarkRequest> bookMarks;

  @NotNull
  @EnumValid(enumClass = ENetworkType.class)
  private String network;
}
