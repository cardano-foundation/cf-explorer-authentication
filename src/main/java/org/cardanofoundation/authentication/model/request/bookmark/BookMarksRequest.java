package org.cardanofoundation.authentication.model.request.bookmark;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.explorer.common.validation.EnumValid;

@Getter
@Setter
public class BookMarksRequest {

  @NotNull private List<BookMarkRequest> bookMarks;

  @NotNull
  @EnumValid(enumClass = ENetworkType.class)
  private String network;
}
