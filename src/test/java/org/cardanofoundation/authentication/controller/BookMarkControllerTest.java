package org.cardanofoundation.authentication.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;

import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.enums.EBookMarkType;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.bookmark.BookMarkRequest;
import org.cardanofoundation.authentication.model.response.BookMarkResponse;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.repository.TokenAuthRepository;
import org.cardanofoundation.authentication.service.BookMarkService;

@WebMvcTest(BookMarkController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
class BookMarkControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private BookMarkService bookMarkService;

  @MockBean private JwtProvider jwtProvider;

  @MockBean private TokenAuthRepository tokenAuthRepository;

  @MockBean private KeycloakProvider keycloakProvider;

  @Test
  void whenCallAdd() throws Exception {
    BookMarkRequest request = new BookMarkRequest();
    request.setKeyword("1");
    request.setNetwork(ENetworkType.MAIN_NET.name());
    request.setType(EBookMarkType.POOL.name());
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    BookMarkResponse res =
        BookMarkResponse.builder()
            .keyword("1")
            .network(ENetworkType.MAIN_NET.name())
            .type(EBookMarkType.POOL.name())
            .build();
    given(bookMarkService.addBookMark(request, httpServletRequest)).willReturn(res);
    mockMvc
        .perform(
            post("/api/v1/bookmark/add")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallFindAll() throws Exception {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    BookMarkResponse bookMarkResponse =
        BookMarkResponse.builder()
            .keyword("1")
            .network(ENetworkType.MAIN_NET.name())
            .type(EBookMarkType.POOL.name())
            .build();
    BasePageResponse<BookMarkResponse> res = new BasePageResponse<>();
    res.setData(List.of(bookMarkResponse));
    given(
            bookMarkService.findBookMarkByType(
                httpServletRequest,
                EBookMarkType.POOL.name(),
                ENetworkType.MAIN_NET.name(),
                PageRequest.of(0, 1)))
        .willReturn(res);
    mockMvc
        .perform(
            get("/api/v1/bookmark/find-all")
                .param("network", String.valueOf(ENetworkType.MAIN_NET))
                .param("type", String.valueOf(EBookMarkType.POOL))
                .param("page", "0")
                .param("size", "1")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenDelete() throws Exception {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    MessageResponse res =
        MessageResponse.builder()
            .code(CommonConstant.CODE_SUCCESS)
            .message(CommonConstant.RESPONSE_SUCCESS)
            .build();
    given(
            bookMarkService.deleteBookMark(
                EBookMarkType.POOL.name(), ENetworkType.MAIN_NET.name(), "1", httpServletRequest))
        .willReturn(res);
    mockMvc
        .perform(
            delete("/api/v1/bookmark/delete")
                .param("type", EBookMarkType.POOL.name())
                .param("network", ENetworkType.MAIN_NET.name())
                .param("keyword", "1")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallFindAllKey() throws Exception {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    BookMarkResponse bookMarkResponse =
        BookMarkResponse.builder()
            .keyword("1")
            .network(ENetworkType.MAIN_NET.name())
            .type(EBookMarkType.POOL.name())
            .build();
    List<BookMarkResponse> res = List.of(bookMarkResponse);
    given(bookMarkService.findKeyBookMark(httpServletRequest, ENetworkType.MAIN_NET.name()))
        .willReturn(res);
    mockMvc
        .perform(
            get("/api/v1/bookmark/find-all-key")
                .param("network", String.valueOf(ENetworkType.MAIN_NET))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  private String asJsonString(final Object obj) {
    try {
      return new Gson().toJson(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
