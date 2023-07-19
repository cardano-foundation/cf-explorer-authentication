package org.cardanofoundation.authentication.service.impl;

import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.mapper.BookMarkMapper;
import org.cardanofoundation.authentication.model.entity.BookMarkEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.enums.EBookMarkType;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.bookmark.BookMarkRequest;
import org.cardanofoundation.authentication.model.request.bookmark.BookMarksRequest;
import org.cardanofoundation.authentication.model.response.AddBookMarkResponse;
import org.cardanofoundation.authentication.model.response.BookMarkResponse;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.repository.BookMarkRepository;
import org.cardanofoundation.authentication.service.BookMarkService;
import org.cardanofoundation.authentication.service.UserService;
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
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserEntity user = userService.findByAccountId(accountId);
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
      HttpServletRequest httpServletRequest, String bookMarkType, String network,
      Pageable pageable) {
    BasePageResponse<BookMarkResponse> response = new BasePageResponse<>();
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserEntity user = userService.findByAccountId(accountId);
    Page<BookMarkEntity> bookMarkPage = bookMarkRepository.findAllBookMarkByUserAndType(
        user.getId(),
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
      String network) {
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserEntity user = userService.findByAccountId(accountId);
    List<BookMarkEntity> bookMarks = bookMarkRepository.findAllKeyBookMarkByUser(user.getId(),
        network);
    return bookMarkMapper.listEntityToResponse(bookMarks);
  }

  @Override
  public AddBookMarkResponse addBookMarks(BookMarksRequest bookMarksRequest,
      HttpServletRequest httpServletRequest) {
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserEntity user = userService.findByAccountId(accountId);
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
