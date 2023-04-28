package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.constant.CommonConstant;
import com.sotatek.authservice.mapper.BookMarkMapper;
import com.sotatek.authservice.model.entity.BookMarkEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.EBookMarkType;
import com.sotatek.authservice.model.enums.ENetworkType;
import com.sotatek.authservice.model.request.bookmark.BookMarkRequest;
import com.sotatek.authservice.model.request.bookmark.BookMarksRequest;
import com.sotatek.authservice.model.response.AddBookMarkResponse;
import com.sotatek.authservice.model.response.BookMarkResponse;
import com.sotatek.authservice.model.response.MessageResponse;
import com.sotatek.authservice.model.response.base.BasePageResponse;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.repository.BookMarkRepository;
import com.sotatek.authservice.service.BookMarkService;
import com.sotatek.authservice.service.UserService;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
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

  private final UserService userService;

  private final JwtProvider jwtProvider;

  private final BookMarkRepository bookMarkRepository;

  private static final BookMarkMapper bookMarkMapper = BookMarkMapper.INSTANCE;

  @Override
  public BookMarkResponse addBookMark(BookMarkRequest bookMarkRequest,
      HttpServletRequest httpServletRequest) {
    String username = jwtProvider.getUserNameFromJwtToken(httpServletRequest);
    UserEntity user = userService.findByUsername(username);
    if (Objects.nonNull(
        bookMarkRepository.checkExistBookMark(user.getId(), bookMarkRequest.getKeyword(),
            bookMarkRequest.getType(), bookMarkRequest.getNetwork()))) {
      throw new BusinessException(CommonErrorCode.BOOKMARK_IS_EXIST);
    }
    Integer countCurrent = bookMarkRepository.getCountBookMarkByUser(user.getId(),
        bookMarkRequest.getNetwork());
    if (countCurrent >= CommonConstant.LIMIT_BOOKMARK) {
      throw new BusinessException(CommonErrorCode.LIMIT_BOOKMARK_IS_2000);
    }
    BookMarkEntity bookMark = bookMarkMapper.requestToEntity(bookMarkRequest);
    bookMark.setUser(user);
    BookMarkEntity bookMarkRes = bookMarkRepository.save(bookMark);
    return bookMarkMapper.entityToResponse(bookMarkRes);
  }

  @Override
  public BasePageResponse<BookMarkResponse> findBookMarkByType(
      HttpServletRequest httpServletRequest, EBookMarkType bookMarkType, ENetworkType network,
      Pageable pageable) {
    BasePageResponse<BookMarkResponse> response = new BasePageResponse<>();
    String username = jwtProvider.getUserNameFromJwtToken(httpServletRequest);
    Page<BookMarkEntity> bookMarkPage = bookMarkRepository.findAllBookMarkByUserAndType(username,
        bookMarkType, network, pageable);
    if (!bookMarkPage.isEmpty()) {
      response.setData(bookMarkMapper.listEntityToResponse(bookMarkPage.getContent()));
    }
    response.setTotalItems(bookMarkPage.getTotalElements());
    return response;
  }

  @Override
  public MessageResponse deleteById(Long bookMarkId) {
    BookMarkEntity bookMark = bookMarkRepository.findById(bookMarkId)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
    bookMarkRepository.delete(bookMark);
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public List<BookMarkResponse> findKeyBookMark(HttpServletRequest httpServletRequest,
      ENetworkType network) {
    String username = jwtProvider.getUserNameFromJwtToken(httpServletRequest);
    List<BookMarkEntity> bookMarks = bookMarkRepository.findAllKeyBookMarkByUser(username, network);
    return bookMarkMapper.listEntityToResponse(bookMarks);
  }

  @Override
  public AddBookMarkResponse addBookMarks(BookMarksRequest bookMarksRequest,
      HttpServletRequest httpServletRequest) {
    String username = jwtProvider.getUserNameFromJwtToken(httpServletRequest);
    UserEntity user = userService.findByUsername(username);
    AtomicReference<Integer> pass = new AtomicReference<>(0);
    AtomicReference<Integer> fail = new AtomicReference<>(0);
    bookMarksRequest.getBookMarks().forEach(bookMarkRequest -> {
      if (Objects.isNull(
          bookMarkRepository.checkExistBookMark(user.getId(), bookMarkRequest.getKeyword(),
              bookMarkRequest.getType(), bookMarkRequest.getNetwork()))) {
        Integer countCurrent = bookMarkRepository.getCountBookMarkByUser(user.getId(),
            bookMarkRequest.getNetwork());
        if (countCurrent < CommonConstant.LIMIT_BOOKMARK) {
          BookMarkEntity bookMark = bookMarkMapper.requestToEntity(bookMarkRequest);
          bookMark.setUser(user);
          bookMarkRepository.save(bookMark);
          pass.getAndSet(pass.get() + 1);
        } else {
          fail.getAndSet(fail.get() + 1);
        }
      }
    });
    return AddBookMarkResponse.builder().passNumber(pass.get()).failNumber(fail.get()).build();
  }
}
