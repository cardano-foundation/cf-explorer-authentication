package org.cardanofoundation.authentication.model.request.bookmark;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarksRequest {

  private List<BookMarkRequest> bookMarks;
}
