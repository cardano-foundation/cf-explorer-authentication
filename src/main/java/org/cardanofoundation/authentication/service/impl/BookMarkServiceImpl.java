package org.cardanofoundation.authentication.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.enums.EBookMarkType;
import org.cardanofoundation.authentication.model.request.bookmark.BookMarkRequest;
import org.cardanofoundation.authentication.model.response.BookMarkResponse;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.service.BookMarkService;
import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookMarkServiceImpl implements BookMarkService {

  private final JwtProvider jwtProvider;

  private final KeycloakProvider keycloakProvider;


  @Override
  public BookMarkResponse addBookMark(BookMarkRequest bookMarkRequest,
      HttpServletRequest httpServletRequest) {
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserRepresentation user = keycloakProvider.getResource().get(accountId).toRepresentation();
    Map<String, List<String>> attributes = user.getAttributes();
    String bookmarkKey =
        CommonConstant.ATTRIBUTE_BOOKMARK + bookMarkRequest.getNetwork() + "_"
            + bookMarkRequest.getType();
    List<String> bookmarkList = null;
    if (Objects.nonNull(attributes) && Objects.nonNull(attributes.get(bookmarkKey))) {
      bookmarkList = attributes.get(bookmarkKey);
      if (bookmarkList.stream().anyMatch(key -> key.contains(bookMarkRequest.getKeyword()))) {
        throw new BusinessException(CommonErrorCode.BOOKMARK_IS_EXIST);
      }
    }
    if (Objects.nonNull(bookmarkList) && bookmarkList.size() >= CommonConstant.LIMIT_BOOKMARK) {
      throw new BusinessException(CommonErrorCode.LIMIT_BOOKMARK_IS_2000);
    }
    if (Objects.isNull(attributes)) {
      attributes = new HashMap<>();
    }
    if (Objects.isNull(bookmarkList)) {
      bookmarkList = new ArrayList<>();
    }
    Instant addTime = Instant.now();
    bookmarkList.add(
        bookMarkRequest.getKeyword() + CommonConstant.ATTRIBUTE_BOOKMARK_ADD_TIME + addTime);
    attributes.put(bookmarkKey, bookmarkList);
    user.setAttributes(attributes);
    keycloakProvider.getResource().get(user.getId()).update(user);
    return BookMarkResponse.builder().type(bookMarkRequest.getType()).createdDate(addTime)
        .keyword(bookMarkRequest.getKeyword()).network(bookMarkRequest.getNetwork()).build();
  }

  @Override
  public BasePageResponse<BookMarkResponse> findBookMarkByType(
      HttpServletRequest httpServletRequest, String bookMarkType, String network,
      Pageable pageable) {
    BasePageResponse<BookMarkResponse> response = new BasePageResponse<>();
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserRepresentation user = keycloakProvider.getResource().get(accountId).toRepresentation();
    Map<String, List<String>> attributes = user.getAttributes();
    String bookmarkKey = CommonConstant.ATTRIBUTE_BOOKMARK + network + "_" + bookMarkType;
    List<BookMarkResponse> bookMarkResponseList = new ArrayList<>();
    List<String> bookmarkList;
    int size = 0;
    if (Objects.nonNull(attributes) && Objects.nonNull(attributes.get(bookmarkKey))) {
      bookmarkList = attributes.get(bookmarkKey);
      size = bookmarkList.size();
      int start = (int) pageable.getOffset();
      int end = Math.min((start + pageable.getPageSize()), size);
      List<String> bookmarkPage = bookmarkList.subList(start, end);
      bookmarkPage.forEach(value -> bookMarkResponseList.add(
          BookMarkResponse.builder().keyword(
                  StringUtils.substringBefore(value, CommonConstant.ATTRIBUTE_BOOKMARK_ADD_TIME))
              .createdDate(Instant.parse(
                  StringUtils.substringAfter(value, CommonConstant.ATTRIBUTE_BOOKMARK_ADD_TIME)))
              .type(bookMarkType)
              .network(network)
              .build()));
    }
    response.setTotalItems(size);
    response.setData(bookMarkResponseList);
    return response;
  }

  @Override
  public MessageResponse deleteBookMark(String type, String network, String keyword,
      HttpServletRequest httpServletRequest) {
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserRepresentation user = keycloakProvider.getResource().get(accountId).toRepresentation();
    Map<String, List<String>> attributes = user.getAttributes();
    String bookmarkKey = CommonConstant.ATTRIBUTE_BOOKMARK + network + "_" + type;
    boolean deleteFlag = false;
    if (Objects.nonNull(attributes) && Objects.nonNull(attributes.get(bookmarkKey))) {
      List<String> bookmarkList = attributes.get(bookmarkKey);
      for (String val : bookmarkList) {
        if (val.contains(keyword)) {
          bookmarkList.remove(val);
          deleteFlag = true;
          break;
        }
      }
      attributes.put(bookmarkKey, bookmarkList);
      user.setAttributes(attributes);
      keycloakProvider.getResource().get(accountId).update(user);
    }
    return deleteFlag ? new MessageResponse(CommonConstant.CODE_SUCCESS,
        CommonConstant.RESPONSE_SUCCESS) : new MessageResponse(CommonErrorCode.UNKNOWN_ERROR);
  }

  @Override
  public List<BookMarkResponse> findKeyBookMark(HttpServletRequest httpServletRequest,
      String network) {
    List<BookMarkResponse> response = new ArrayList<>();
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserRepresentation user = keycloakProvider.getResource().get(accountId).toRepresentation();
    Map<String, List<String>> attributes = user.getAttributes();
    List<String> bookMarkKeys = Arrays.asList(EBookMarkType.STAKE_KEY.name(),
        EBookMarkType.POOL.name(), EBookMarkType.ADDRESS.name(), EBookMarkType.BLOCK.name(),
        EBookMarkType.EPOCH.name(), EBookMarkType.TRANSACTION.name());
    if (Objects.nonNull(attributes)) {
      bookMarkKeys.forEach(key -> {
        String bookmarkKey = CommonConstant.ATTRIBUTE_BOOKMARK + network + "_" + key;
        List<String> bookmarkList = attributes.get(bookmarkKey);
        if (Objects.nonNull(bookmarkList)) {
          bookmarkList.forEach(value -> response.add(
              BookMarkResponse.builder().keyword(
                      StringUtils.substringBefore(value, CommonConstant.ATTRIBUTE_BOOKMARK_ADD_TIME))
                  .createdDate(Instant.parse(StringUtils.substringAfter(value,
                      CommonConstant.ATTRIBUTE_BOOKMARK_ADD_TIME)))
                  .network(network)
                  .type(key)
                  .build()));
        }
      });
    }
    return response;
  }
}
