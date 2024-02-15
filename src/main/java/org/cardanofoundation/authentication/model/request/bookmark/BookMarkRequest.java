package org.cardanofoundation.authentication.model.request.bookmark;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cardanofoundation.authentication.model.enums.EBookMarkType;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.explorer.common.validation.EnumValid;

@Getter
@Setter
public class BookMarkRequest {

  private String urlPage;

  @NotNull @NotBlank private String keyword;

  @NotNull
  @EnumValid(enumClass = EBookMarkType.class)
  private String type;

  @NotNull
  @EnumValid(enumClass = ENetworkType.class)
  private String network;
}
