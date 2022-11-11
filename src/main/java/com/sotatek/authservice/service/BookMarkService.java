package com.sotatek.authservice.service;

import com.sotatek.authservice.model.dto.BookMarkDto;
import com.sotatek.authservice.model.enums.EBookMarkType;
import com.sotatek.authservice.model.request.bookmark.BookMarkRequest;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface BookMarkService {

  Long addBookMark(BookMarkRequest bookMarkRequest, HttpServletRequest httpServletRequest);

  List<BookMarkDto> findBookMarkByAccount(HttpServletRequest httpServletRequest);

  List<BookMarkDto> findBookMarkByType(HttpServletRequest httpServletRequest,
      EBookMarkType bookMarkType);

  Boolean deleteByAccount(HttpServletRequest httpServletRequest);

  Boolean deleteByType(HttpServletRequest httpServletRequest, EBookMarkType bookMarkType);

  Boolean deleteById(Long bookMarkId);
}
