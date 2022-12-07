package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.constant.CommonConstant;
import com.sotatek.authservice.mapper.BookMarkMapper;
import com.sotatek.authservice.model.entity.BookMarkEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.EBookMarkType;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.request.bookmark.BookMarkRequest;
import com.sotatek.authservice.model.response.BookMarkResponse;
import com.sotatek.authservice.model.response.base.BasePageResponse;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.repository.BookMarkRepository;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.service.BookMarkService;
import com.sotatek.authservice.service.UserHistoryService;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookMarkServiceImpl implements BookMarkService {

  private final UserRepository userRepository;

  private final JwtProvider jwtProvider;

  private final BookMarkRepository bookMarkRepository;

  private final UserHistoryService userHistoryService;

  private static final BookMarkMapper bookMarkMapper = BookMarkMapper.INSTANCE;

  @Override
  public Long addBookMark(BookMarkRequest bookMarkRequest, HttpServletRequest httpServletRequest) {
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    if (bookMarkRepository.checkExistBookMark(user.getId(), bookMarkRequest.getKeyword(),
        bookMarkRequest.getType()) != null) {
      throw new BusinessException(CommonErrorCode.BOOKMARK_IS_EXIST);
    }
    Integer countCurrent = bookMarkRepository.getCountBookMarkByUser(user.getId());
    if (countCurrent > CommonConstant.LIMIT_BOOKMARK) {
      throw new BusinessException(CommonErrorCode.LIMIT_BOOKMARK_IS_2000);
    }
    BookMarkEntity bookMark = bookMarkMapper.requestToEntity(bookMarkRequest);
    bookMark.setUser(user);
    BookMarkEntity bookMarkSave = bookMarkRepository.save(bookMark);
    userHistoryService.saveUserHistory(EUserAction.ADD_BOOKMARK, null, Instant.now(), true,
        bookMarkRequest.getType() + "/" + bookMarkRequest.getKeyword(), user);
    return bookMarkSave.getId();
  }

  @Override
  public BasePageResponse<BookMarkResponse> findBookMarkByType(
      HttpServletRequest httpServletRequest, EBookMarkType bookMarkType, Pageable pageable) {
    BasePageResponse<BookMarkResponse> response = new BasePageResponse<>();
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    Page<BookMarkEntity> bookMarkPage = bookMarkRepository.findAllBookMarkByUserAndType(username,
        bookMarkType, pageable);
    if (!bookMarkPage.isEmpty()) {
      response.setData(bookMarkMapper.listEntityToResponse(bookMarkPage.getContent()));
    }
    response.setTotalItems(bookMarkPage.getTotalElements());
    return response;
  }

  @Override
  public Boolean deleteById(Long bookMarkId) {
    BookMarkEntity bookMark = bookMarkRepository.findById(bookMarkId)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
    userHistoryService.saveUserHistory(EUserAction.REMOVE_BOOKMARK, null, Instant.now(), true,
        bookMark.getType() + "/" + bookMark.getKeyword(), bookMark.getUser());
    bookMarkRepository.delete(bookMark);
    return true;
  }
}
