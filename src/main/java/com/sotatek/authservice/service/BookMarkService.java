package com.sotatek.authservice.service;

import com.sotatek.authservice.model.dto.BookMarkDto;
import com.sotatek.authservice.model.enums.EBookMarkType;
import com.sotatek.authservice.model.request.bookmark.BookMarkRequest;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface BookMarkService {

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: add bookmark
   * @update:
   */
  Long addBookMark(BookMarkRequest bookMarkRequest, HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: find all bookmark by username
   * @update:
   */
  List<BookMarkDto> findBookMarkByAccount(HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: find bookmark by type
   * @update:
   */
  List<BookMarkDto> findBookMarkByType(HttpServletRequest httpServletRequest,
      EBookMarkType bookMarkType);

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: delete bookmark by username
   * @update:
   */
  Boolean deleteByAccount(HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: delete bookmark by type
   * @update:
   */
  Boolean deleteByType(HttpServletRequest httpServletRequest, EBookMarkType bookMarkType);

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: delete bookmark by id
   * @update:
   */
  Boolean deleteById(Long bookMarkId);
}
