package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.mapper.BookMarkMapper;
import com.sotatek.authservice.model.dto.BookMarkDto;
import com.sotatek.authservice.model.entity.BookMarkEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.EBookMarkType;
import com.sotatek.authservice.model.request.bookmark.BookMarkRequest;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.repository.BookMarkRepository;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.service.BookMarkService;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class BookMarkServiceImpl implements BookMarkService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private BookMarkRepository bookMarkRepository;

  private BookMarkMapper bookMarkMapper = BookMarkMapper.INSTANCE;

  @Override
  public Long addBookMark(BookMarkRequest bookMarkRequest, HttpServletRequest httpServletRequest) {
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    UserEntity user = userRepository.findByUsernameAndIsDeletedFalse(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    BookMarkEntity bookMark = bookMarkMapper.requestToEntity(bookMarkRequest);
    bookMark.setAccessTime(Instant.now());
    bookMark.setUser(user);
    BookMarkEntity bookMarkSave = bookMarkRepository.save(bookMark);
    return bookMarkSave.getId();
  }

  @Override
  public List<BookMarkDto> findBookMarkByAccount(HttpServletRequest httpServletRequest) {
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    List<BookMarkEntity> bookMarks = bookMarkRepository.findAllBookMarkByUsername(username);
    return bookMarkMapper.listEntityToDto(bookMarks);
  }

  @Override
  public List<BookMarkDto> findBookMarkByType(HttpServletRequest httpServletRequest,
      EBookMarkType bookMarkType) {
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    List<BookMarkEntity> bookMarks = bookMarkRepository.findAllBookMarkByUsernameAndType(username,
        bookMarkType);
    return bookMarkMapper.listEntityToDto(bookMarks);
  }

  @Override
  public Boolean deleteByAccount(HttpServletRequest httpServletRequest) {
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    List<BookMarkEntity> bookMarks = bookMarkRepository.findAllBookMarkByUsername(username);
    if (bookMarks != null) {
      bookMarks.forEach(bookMark -> bookMarkRepository.delete(bookMark));
    }
    return true;
  }

  @Override
  public Boolean deleteByType(HttpServletRequest httpServletRequest, EBookMarkType bookMarkType) {
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    List<BookMarkEntity> bookMarks = bookMarkRepository.findAllBookMarkByUsernameAndType(username,
        bookMarkType);
    if (bookMarks != null) {
      bookMarks.forEach(bookMark -> bookMarkRepository.delete(bookMark));
    }
    return true;
  }

  @Override
  public Boolean deleteById(Long bookMarkId) {
    BookMarkEntity bookMark = bookMarkRepository.findById(bookMarkId)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
    bookMarkRepository.delete(bookMark);
    return true;
  }
}
