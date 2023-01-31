package com.sotatek.authservice.service;

import com.sotatek.authservice.model.enums.EBookMarkType;
import com.sotatek.authservice.model.request.bookmark.BookMarkRequest;
import com.sotatek.authservice.model.request.bookmark.BookMarksRequest;
import com.sotatek.authservice.model.response.BookMarkResponse;
import com.sotatek.authservice.model.response.MessageResponse;
import com.sotatek.authservice.model.response.base.BasePageResponse;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;

public interface BookMarkService {

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: add bookmark
   * @update: 10/1/2023
   */
  MessageResponse addBookMark(BookMarkRequest bookMarkRequest,
      HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: find bookmark by type
   * @update: 05/12/2022
   */
  BasePageResponse<BookMarkResponse> findBookMarkByType(HttpServletRequest httpServletRequest,
      EBookMarkType bookMarkType, Pageable pageable);

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
  List<String> findKeyBookMark(HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 31/01/2023
   * description: add bookmark list
   * @update:
   */
  List<String> addBookMarks(BookMarksRequest bookMarksRequest,
      HttpServletRequest httpServletRequest);
}
