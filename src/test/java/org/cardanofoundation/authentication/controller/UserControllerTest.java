package org.cardanofoundation.authentication.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.EditUserRequest;
import org.cardanofoundation.authentication.model.response.UserInfoResponse;
import org.cardanofoundation.authentication.model.response.UserResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.RedisProvider;
import org.cardanofoundation.authentication.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @MockBean
  private JwtProvider jwtProvider;

  @MockBean
  private RedisProvider redisProvider;

  private final String ADDRESS_WALLET = "stake1u80n7nvm3qlss9ls0krp5xh7sqxlazp8kz6n3fp5sgnul5cnxyg4p";

  @Test
  void whenCallEdit() throws Exception {
    EditUserRequest request = new EditUserRequest();
    request.setAddress(ADDRESS_WALLET);
    request.setEmail("Test@gmail.com");
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    UserResponse res = new UserResponse();
    res.setAddress(ADDRESS_WALLET);
    res.setEmail("Test@gmail.com");
    given(userService.editUser(request, httpServletRequest)).willReturn(res);
    mockMvc.perform(put("/api/v1/user/edit")
            .content(asJsonString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallInfo() throws Exception {
    UserInfoResponse res = UserInfoResponse.builder().email("Test@gmail.com")
        .address(ADDRESS_WALLET).build();
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    given(userService.infoUser(httpServletRequest, ENetworkType.MAIN_NET.name())).willReturn(res);
    mockMvc.perform(get("/api/v1/user/info")
            .param("network", String.valueOf(ENetworkType.MAIN_NET))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallExistEmail() throws Exception {
    given(userService.checkExistEmail("Test@gmail.com")).willReturn(true);
    mockMvc.perform(get("/api/v1/user/exist-email")
            .param("email", "Test@gmail.com")
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
