package org.cardanofoundation.authentication.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddBookMarkResponse {

  private Integer passNumber;

  private Integer failNumber;
}
