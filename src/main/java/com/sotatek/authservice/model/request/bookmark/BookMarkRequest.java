package com.sotatek.authservice.model.request.bookmark;

import com.sotatek.authservice.model.enums.EBookMarkType;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BookMarkRequest {

  private String urlPage;

  @NonNull
  @NotBlank
  private String keyword;

  @NonNull
  @NotBlank
  private EBookMarkType type;

}
