package org.cardanofoundation.authentication.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.cardanofoundation.authentication.constant.CommonConstant;
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
import org.cardanofoundation.authentication.service.impl.BookMarkServiceImpl;
import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookMarkServiceTest {

  @InjectMocks
  private BookMarkServiceImpl bookMarkService;

  @Mock
  private JwtProvider jwtProvider;

  @Mock
  private UserService userService;

  @Mock
  private BookMarkRepository bookMarkRepository;

  private final String ADDRESS_WALLET = "stake1u80n7nvm3qlss9ls0krp5xh7sqxlazp8kz6n3fp5sgnul5cnxyg4p";

  @Test
  void whenAddBookMark_IsExist_throwException() {
    BookMarkRequest bookMarkRequest = new BookMarkRequest();
    bookMarkRequest.setKeyword("1");
    bookMarkRequest.setType(EBookMarkType.POOL.name());
    bookMarkRequest.setNetwork(ENetworkType.MAIN_NET.name());
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    when(bookMarkRepository.checkExistBookMark(1L, "1",
        EBookMarkType.POOL.name(), ENetworkType.MAIN_NET.name())).thenReturn(1L);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      bookMarkService.addBookMark(bookMarkRequest, httpServletRequest);
    });
    String expectedCode = CommonErrorCode.BOOKMARK_IS_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenAddBookMark_IsNotExistAndExceedLimit_throwException() {
    BookMarkRequest bookMarkRequest = new BookMarkRequest();
    bookMarkRequest.setKeyword("1");
    bookMarkRequest.setType(EBookMarkType.POOL.name());
    bookMarkRequest.setNetwork(ENetworkType.MAIN_NET.name());
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    when(bookMarkRepository.checkExistBookMark(1L, "1",
        EBookMarkType.POOL.name(), ENetworkType.MAIN_NET.name())).thenReturn(null);
    when(bookMarkRepository.getCountBookMarkByUser(1L, ENetworkType.MAIN_NET.name())).thenReturn(
        2500);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      bookMarkService.addBookMark(bookMarkRequest, httpServletRequest);
    });
    String expectedCode = CommonErrorCode.LIMIT_BOOKMARK_IS_2000.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenFindBookMarkByType_IsNotExist_returnResponseEmpty() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    when(bookMarkRepository.findAllBookMarkByUserAndType(1L,
        EBookMarkType.POOL.name(), ENetworkType.MAIN_NET.name(), PageRequest.of(0, 10))).thenReturn(
        Page.empty());
    BasePageResponse<BookMarkResponse> response = bookMarkService.findBookMarkByType(
        httpServletRequest, EBookMarkType.POOL.name(), ENetworkType.MAIN_NET.name(),
        PageRequest.of(0, 10));
    Assertions.assertEquals(0, response.getTotalItems());
  }

  @Test
  void whenFindBookMarkByType_IsExist_returnResponse() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    BookMarkEntity bookMark = new BookMarkEntity();
    bookMark.setType(EBookMarkType.POOL.name());
    bookMark.setKeyword("1");
    bookMark.setNetwork(ENetworkType.MAIN_NET.name());
    when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    when(bookMarkRepository.findAllBookMarkByUserAndType(1L,
        EBookMarkType.POOL.name(), ENetworkType.MAIN_NET.name(), PageRequest.of(0, 10))).thenReturn(
        new PageImpl<>(List.of(bookMark)));
    BasePageResponse<BookMarkResponse> response = bookMarkService.findBookMarkByType(
        httpServletRequest, EBookMarkType.POOL.name(), ENetworkType.MAIN_NET.name(),
        PageRequest.of(0, 10));
    Assertions.assertEquals(1, response.getTotalItems());
  }

  @Test
  void whenDeleteById_IsNotExist_throwException() {
    when(bookMarkRepository.findById(1L)).thenReturn(Optional.empty());
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      bookMarkService.deleteById(1L);
    });
    String expectedCode = CommonErrorCode.UNKNOWN_ERROR.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenDeleteById_IsExist_returnResponse() {
    BookMarkEntity bookMark = BookMarkEntity.builder().keyword("1")
        .network(ENetworkType.MAIN_NET.name()).type(EBookMarkType.POOL.name()).build();
    bookMark.setId(1L);
    when(bookMarkRepository.findById(1L)).thenReturn(Optional.of(bookMark));
    doNothing().when(bookMarkRepository).delete(bookMark);
    MessageResponse response = bookMarkService.deleteById(1L);
    String expectedCode = CommonConstant.CODE_SUCCESS;
    Assertions.assertEquals(expectedCode, response.getCode());
  }

  @Test
  void whenFindKeyBookMark_IsNotExist_returnEmpty() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    when(bookMarkRepository.findAllKeyBookMarkByUser(1L, ENetworkType.MAIN_NET.name())).thenReturn(
        List.of());
    List<BookMarkResponse> response = bookMarkService.findKeyBookMark(httpServletRequest,
        ENetworkType.MAIN_NET.name());
    Assertions.assertEquals(0, response.size());
  }

  @Test
  void whenFindKeyBookMark_IsExist_returnResponse() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    BookMarkEntity bookMark = new BookMarkEntity();
    bookMark.setNetwork(ENetworkType.MAIN_NET.name());
    bookMark.setType(EBookMarkType.POOL.name());
    bookMark.setId(1L);
    when(bookMarkRepository.findAllKeyBookMarkByUser(1L, ENetworkType.MAIN_NET.name())).thenReturn(
        List.of(bookMark));
    List<BookMarkResponse> response = bookMarkService.findKeyBookMark(httpServletRequest,
        ENetworkType.MAIN_NET.name());
    Assertions.assertEquals(1, response.size());
  }

  @Test
  void whenAddBookMarks_1Success_1Fail() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    BookMarksRequest bookMarksRequest = new BookMarksRequest();
    BookMarkRequest bookMarkRequest1 = new BookMarkRequest();
    bookMarkRequest1.setKeyword("1");
    bookMarkRequest1.setType(EBookMarkType.POOL.name());
    bookMarkRequest1.setNetwork(ENetworkType.MAIN_NET.name());
    bookMarksRequest.setBookMarks(List.of(bookMarkRequest1));
    when(bookMarkRepository.checkExistBookMark(1L, "1",
        EBookMarkType.POOL.name(), ENetworkType.MAIN_NET.name())).thenReturn(null);
    when(bookMarkRepository.getCountBookMarkByUser(1L, ENetworkType.MAIN_NET.name())).thenReturn(
        1500);
    BookMarkEntity bookMark = BookMarkEntity.builder().keyword("1").type(EBookMarkType.POOL.name())
        .network(ENetworkType.MAIN_NET.name()).build();
    bookMark.setUser(user);
    when(bookMarkRepository.save(bookMark)).thenReturn(null);
    AddBookMarkResponse response = bookMarkService.addBookMarks(bookMarksRequest,
        httpServletRequest);
    Assertions.assertEquals(1, response.getPassNumber());
  }
}
