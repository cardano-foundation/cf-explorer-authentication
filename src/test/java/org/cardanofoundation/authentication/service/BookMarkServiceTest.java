package org.cardanofoundation.authentication.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.data.domain.PageRequest;

import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.enums.EBookMarkType;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.bookmark.BookMarkRequest;
import org.cardanofoundation.authentication.model.response.BookMarkResponse;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.service.impl.BookMarkServiceImpl;
import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookMarkServiceTest {

  @InjectMocks private BookMarkServiceImpl bookMarkService;

  @Mock private JwtProvider jwtProvider;

  @Mock private KeycloakProvider keycloakProvider;

  private final String ADDRESS_WALLET =
      "stake1u80n7nvm3qlss9ls0krp5xh7sqxlazp8kz6n3fp5sgnul5cnxyg4p";

  @Test
  void whenAddBookMark_IsExist_throwException() {
    BookMarkRequest bookMarkRequest = new BookMarkRequest();
    bookMarkRequest.setKeyword("1");
    bookMarkRequest.setType(EBookMarkType.POOL.name());
    bookMarkRequest.setNetwork(ENetworkType.MAIN_NET.name());
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(ADDRESS_WALLET)).thenReturn(userResource);
    UserRepresentation user = Mockito.mock(UserRepresentation.class);
    when(userResource.toRepresentation()).thenReturn(user);
    Map<String, List<String>> attributes = new HashMap<>();
    String bookmarkKey =
        CommonConstant.ATTRIBUTE_BOOKMARK
            + bookMarkRequest.getNetwork()
            + "_"
            + bookMarkRequest.getType();
    attributes.put(bookmarkKey, List.of("1"));
    when(user.getAttributes()).thenReturn(attributes);
    BusinessException exception =
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
              bookMarkService.addBookMark(bookMarkRequest, httpServletRequest);
            });
    String expectedCode = CommonErrorCode.BOOKMARK_IS_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenAddBookMark_IsNotExistAndExceedLimit_throwException() {
    BookMarkRequest bookMarkRequest = new BookMarkRequest();
    bookMarkRequest.setKeyword("code");
    bookMarkRequest.setType(EBookMarkType.POOL.name());
    bookMarkRequest.setNetwork(ENetworkType.MAIN_NET.name());
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(ADDRESS_WALLET)).thenReturn(userResource);
    UserRepresentation user = Mockito.mock(UserRepresentation.class);
    when(userResource.toRepresentation()).thenReturn(user);
    Map<String, List<String>> attributes = new HashMap<>();
    List<String> keys = new ArrayList<>();
    for (int i = 0; i < 2005; i++) {
      keys.add("key=" + i);
    }
    String bookmarkKey =
        CommonConstant.ATTRIBUTE_BOOKMARK
            + bookMarkRequest.getNetwork()
            + "_"
            + bookMarkRequest.getType();
    attributes.put(bookmarkKey, keys);
    when(user.getAttributes()).thenReturn(attributes);
    BusinessException exception =
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
              bookMarkService.addBookMark(bookMarkRequest, httpServletRequest);
            });
    String expectedCode = CommonErrorCode.LIMIT_BOOKMARK_IS_2000.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenAddBookMark_IsValid_returnResponse() {
    BookMarkRequest bookMarkRequest = new BookMarkRequest();
    bookMarkRequest.setKeyword("code");
    bookMarkRequest.setType(EBookMarkType.POOL.name());
    bookMarkRequest.setNetwork(ENetworkType.MAIN_NET.name());
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(ADDRESS_WALLET)).thenReturn(userResource);
    UserRepresentation user = Mockito.mock(UserRepresentation.class);
    when(user.getId()).thenReturn(ADDRESS_WALLET);
    when(userResource.toRepresentation()).thenReturn(user);
    Map<String, List<String>> attributes = new HashMap<>();
    when(user.getAttributes()).thenReturn(attributes);
    doNothing().when(userResource).update(any());
    BookMarkResponse response = bookMarkService.addBookMark(bookMarkRequest, httpServletRequest);
    Assertions.assertEquals("code", response.getKeyword());
    Assertions.assertEquals(EBookMarkType.POOL.name(), response.getType());
  }

  @Test
  void whenFindBookMarkByType_IsNotExist_returnResponseEmpty() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(ADDRESS_WALLET)).thenReturn(userResource);
    UserRepresentation user = Mockito.mock(UserRepresentation.class);
    when(userResource.toRepresentation()).thenReturn(user);
    when(user.getAttributes()).thenReturn(null);
    BasePageResponse<BookMarkResponse> response =
        bookMarkService.findBookMarkByType(
            httpServletRequest,
            EBookMarkType.POOL.name(),
            ENetworkType.MAIN_NET.name(),
            PageRequest.of(0, 10));
    Assertions.assertEquals(0, response.getTotalItems());
  }

  @Test
  void whenFindBookMarkByType_IsExist_returnResponse() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(ADDRESS_WALLET)).thenReturn(userResource);
    UserRepresentation user = Mockito.mock(UserRepresentation.class);
    when(userResource.toRepresentation()).thenReturn(user);
    Map<String, List<String>> attributes = new HashMap<>();
    String bookmarkKey =
        CommonConstant.ATTRIBUTE_BOOKMARK
            + ENetworkType.MAIN_NET.name()
            + "_"
            + EBookMarkType.POOL.name();
    attributes.put(
        bookmarkKey,
        List.of(
            "pool1pu5jlj4q9w9jlxeu370a3c9myx47md5j5m2str0naunn2q3lkdy"
                + CommonConstant.ATTRIBUTE_BOOKMARK_ADD_TIME
                + Instant.now()));
    when(user.getAttributes()).thenReturn(attributes);
    BasePageResponse<BookMarkResponse> response =
        bookMarkService.findBookMarkByType(
            httpServletRequest,
            EBookMarkType.POOL.name(),
            ENetworkType.MAIN_NET.name(),
            PageRequest.of(0, 10));
    Assertions.assertEquals(1, response.getTotalItems());
  }

  @Test
  void whenDeleteBookmark_isNotExist_returnFail() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(ADDRESS_WALLET)).thenReturn(userResource);
    UserRepresentation user = Mockito.mock(UserRepresentation.class);
    when(userResource.toRepresentation()).thenReturn(user);
    Map<String, List<String>> attributes = new HashMap<>();
    String bookmarkKey =
        CommonConstant.ATTRIBUTE_BOOKMARK
            + ENetworkType.MAIN_NET.name()
            + "_"
            + EBookMarkType.POOL.name();
    attributes.put(bookmarkKey, Arrays.asList("test1", "test2"));
    when(user.getAttributes()).thenReturn(attributes);
    doNothing().when(userResource).update(any());
    MessageResponse response =
        bookMarkService.deleteBookMark(
            EBookMarkType.POOL.name(),
            ENetworkType.MAIN_NET.name(),
            "pool1pu5jlj4q9w9jlxeu370a3c9myx47md5j5m2str0naunn2q3lkdy",
            httpServletRequest);
    Assertions.assertEquals(
        CommonErrorCode.UNKNOWN_ERROR.getServiceErrorCode(), response.getCode());
  }

  @Test
  void whenFindKeyBookMark_IsNotExist_returnEmpty() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(ADDRESS_WALLET)).thenReturn(userResource);
    UserRepresentation user = Mockito.mock(UserRepresentation.class);
    when(userResource.toRepresentation()).thenReturn(user);
    when(user.getAttributes()).thenReturn(null);
    List<BookMarkResponse> response =
        bookMarkService.findKeyBookMark(httpServletRequest, ENetworkType.MAIN_NET.name());
    Assertions.assertEquals(0, response.size());
  }

  @Test
  void whenFindKeyBookMark_IsExist_returnResponse() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UsersResource usersResource = Mockito.mock(UsersResource.class);
    when(keycloakProvider.getResource()).thenReturn(usersResource);
    UserResource userResource = Mockito.mock(UserResource.class);
    when(usersResource.get(ADDRESS_WALLET)).thenReturn(userResource);
    UserRepresentation user = Mockito.mock(UserRepresentation.class);
    when(userResource.toRepresentation()).thenReturn(user);
    Map<String, List<String>> attributes = new HashMap<>();
    String bookmarkKey =
        CommonConstant.ATTRIBUTE_BOOKMARK
            + ENetworkType.MAIN_NET.name()
            + "_"
            + EBookMarkType.POOL.name();
    attributes.put(
        bookmarkKey,
        List.of(
            "pool1pu5jlj4q9w9jlxeu370a3c9myx47md5j5m2str0naunn2q3lkdy"
                + CommonConstant.ATTRIBUTE_BOOKMARK_ADD_TIME
                + Instant.now()));
    when(user.getAttributes()).thenReturn(attributes);
    BasePageResponse<BookMarkResponse> response =
        bookMarkService.findBookMarkByType(
            httpServletRequest,
            EBookMarkType.POOL.name(),
            ENetworkType.MAIN_NET.name(),
            PageRequest.of(0, 10));
    Assertions.assertEquals(1, response.getTotalItems());
  }
}
