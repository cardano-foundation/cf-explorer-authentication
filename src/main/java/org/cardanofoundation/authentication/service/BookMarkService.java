package org.cardanofoundation.authentication.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;

import org.cardanofoundation.authentication.model.request.bookmark.BookMarkRequest;
import org.cardanofoundation.authentication.model.response.BookMarkResponse;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;

public interface BookMarkService {

  /*
   * @since: 10/11/2022
   * description: add bookmark
   * @update: 10/1/2023
   */
  BookMarkResponse addBookMark(
      BookMarkRequest bookMarkRequest, HttpServletRequest httpServletRequest);

  /*
   * @since: 10/11/2022
   * description: find bookmark by type
   * @update: 05/12/2022
   */
  BasePageResponse<BookMarkResponse> findBookMarkByType(
      HttpServletRequest httpServletRequest,
      String bookMarkType,
      String network,
      Pageable pageable);

  /*
   * @since: 10/11/2022
   * description: delete bookmark
   * @update: 10/1/2023
   */
  MessageResponse deleteBookMark(
      String type, String network, String keyword, HttpServletRequest httpServletRequest);

  /*
   * @since: 30/01/2023
   * description: find all bookmark key
   * @update:
   */
  List<BookMarkResponse> findKeyBookMark(HttpServletRequest httpServletRequest, String network);
}
