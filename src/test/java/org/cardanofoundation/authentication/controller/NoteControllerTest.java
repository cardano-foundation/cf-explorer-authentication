package org.cardanofoundation.authentication.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.note.PrivateNoteRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.PrivateNoteResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.RedisProvider;
import org.cardanofoundation.authentication.service.PrivateNoteService;
import org.cardanofoundation.authentication.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NoteController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
class NoteControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PrivateNoteService privateNoteService;

  @MockBean
  private UserService userService;

  @MockBean
  private JwtProvider jwtProvider;

  @MockBean
  private RedisProvider redisProvider;

  @Test
  void whenCallAdd() throws Exception {
    PrivateNoteRequest request = new PrivateNoteRequest();
    request.setTxHash("8e0280beebc3d12626e87b182f4205d75e49981042f54081cd35f3a4a85630b0");
    request.setNetwork(ENetworkType.MAIN_NET.name());
    request.setNote("Note");
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    MessageResponse res = MessageResponse.builder().code(CommonConstant.CODE_SUCCESS)
        .message(CommonConstant.RESPONSE_SUCCESS).build();
    given(privateNoteService.addPrivateNote(request, httpServletRequest)).willReturn(res);
    mockMvc.perform(post("/api/v1/note/add")
            .content(asJsonString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallFindAll() throws Exception {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    PrivateNoteResponse privateNoteResponse = new PrivateNoteResponse();
    privateNoteResponse.setTxHash("txHash123456789");
    privateNoteResponse.setCreatedDate(Instant.now());
    privateNoteResponse.setNote("Note");
    BasePageResponse<PrivateNoteResponse> res = new BasePageResponse<>();
    res.setData(List.of(privateNoteResponse));
    given(privateNoteService.findAllNote(httpServletRequest,
        ENetworkType.MAIN_NET.name(),
        PageRequest.of(0, 1))).willReturn(res);
    mockMvc.perform(get("/api/v1/note/find-all")
            .param("network", String.valueOf(ENetworkType.MAIN_NET))
            .param("page", "0")
            .param("size", "1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenDelete() throws Exception {
    MessageResponse res = MessageResponse.builder().code(CommonConstant.CODE_SUCCESS)
        .message(CommonConstant.RESPONSE_SUCCESS).build();
    given(privateNoteService.deleteById(1L)).willReturn(res);
    mockMvc.perform(delete("/api/v1/note/delete/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallEdit() throws Exception {
    PrivateNoteResponse res = new PrivateNoteResponse();
    res.setTxHash("txHash123456789");
    res.setCreatedDate(Instant.now());
    res.setNote("Note");
    given(privateNoteService.editById(1L, "Note")).willReturn(res);
    mockMvc.perform(put("/api/v1/note/edit")
            .param("note", "Note")
            .param("noteId", "1")
            .contentType(MediaType.APPLICATION_JSON))
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
