package org.cardanofoundation.authentication.model.request.bookmark;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarksRequest {

  @NotNull
  private List<BookMarkRequest> bookMarks;
}
