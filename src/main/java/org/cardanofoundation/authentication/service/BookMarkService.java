package org.cardanofoundation.authentication.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.cardanofoundation.authentication.model.request.bookmark.BookMarkRequest;
import org.cardanofoundation.authentication.model.request.bookmark.BookMarksRequest;
import org.cardanofoundation.authentication.model.response.AddBookMarkResponse;
import org.cardanofoundation.authentication.model.response.BookMarkResponse;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.springframework.data.domain.Pageable;

public interface BookMarkService {

  /*
   * @since: 10/11/2022
   * description: add bookmark
   * @update: 10/1/2023
   */
  BookMarkResponse addBookMark(BookMarkRequest bookMarkRequest,
      HttpServletRequest httpServletRequest);

  /*
   * @since: 10/11/2022
   * description: find bookmark by type
   * @update: 05/12/2022
   */
  BasePageResponse<BookMarkResponse> findBookMarkByType(HttpServletRequest httpServletRequest,
      String bookMarkType, String network, Pageable pageable);

  /*
   * @since: 10/11/2022
   * description: delete bookmark by id
   * @update: 10/1/2023
   */
  MessageResponse deleteById(Long bookMarkId);

  /*
   * @since: 30/01/2023
   * description: find all bookmark key
   * @update:
   */
  List<BookMarkResponse> findKeyBookMark(HttpServletRequest httpServletRequest,
      String network);

  /*
   * @since: 31/01/2023
   * description: add bookmark list
   * @update:
   */
  AddBookMarkResponse addBookMarks(BookMarksRequest bookMarksRequest,
      HttpServletRequest httpServletRequest);
}
