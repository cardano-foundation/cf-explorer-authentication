package com.sotatek.authservice.model.request.bookmark;

import com.sotatek.authservice.model.enums.EBookMarkType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarkRequest {

  private String urlPage;

  private String keyword;

  private EBookMarkType type;

}
