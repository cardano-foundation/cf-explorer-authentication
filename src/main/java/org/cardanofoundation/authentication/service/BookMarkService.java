package org.cardanofoundation.authentication.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.cardanofoundation.authentication.model.enums.EBookMarkType;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.bookmark.BookMarkRequest;
import org.cardanofoundation.authentication.model.request.bookmark.BookMarksRequest;
import org.cardanofoundation.authentication.model.response.AddBookMarkResponse;
import org.cardanofoundation.authentication.model.response.BookMarkResponse;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.springframework.data.domain.Pageable;

public interface BookMarkService {

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: add bookmark
   * @update: 10/1/2023
   */
  BookMarkResponse addBookMark(BookMarkRequest bookMarkRequest,
      HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: find bookmark by type
   * @update: 05/12/2022
   */
  BasePageResponse<BookMarkResponse> findBookMarkByType(HttpServletRequest httpServletRequest,
      String bookMarkType, String network, Pageable pageable);

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: delete bookmark by id
   * @update: 10/1/2023
   */
  MessageResponse deleteById(Long bookMarkId);

  /*
   * @author: phuc.nguyen5
   * @since: 30/01/2023
   * description: find all bookmark key
   * @update:
   */
  List<BookMarkResponse> findKeyBookMark(HttpServletRequest httpServletRequest,
      String network);

  /*
   * @author: phuc.nguyen5
   * @since: 31/01/2023
   * description: add bookmark list
   * @update:
   */
  AddBookMarkResponse addBookMarks(BookMarksRequest bookMarksRequest,
      HttpServletRequest httpServletRequest);
}
