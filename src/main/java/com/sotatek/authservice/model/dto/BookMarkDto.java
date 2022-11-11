package com.sotatek.authservice.model.dto;

import com.sotatek.authservice.model.enums.EBookMarkType;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarkDto {

  private Long id;

  private String tittlePage;

  private String urlPage;

  private String keyword;

  private Instant accessTime;

  private EBookMarkType type;
}
