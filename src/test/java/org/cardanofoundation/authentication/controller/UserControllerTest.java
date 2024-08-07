package org.cardanofoundation.authentication.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;

import org.cardanofoundation.authentication.model.request.event.EventModel;
import org.cardanofoundation.authentication.model.response.UserInfoResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.repository.TokenAuthRepository;
import org.cardanofoundation.authentication.service.KeycloakService;
import org.cardanofoundation.authentication.service.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private KeycloakService keycloakService;

  @MockBean private JwtProvider jwtProvider;

  @MockBean private TokenAuthRepository tokenAuthRepository;

  @MockBean private KeycloakProvider keycloakProvider;

  @MockBean private UserService userService;

  @Test
  void whenCallInfo() throws Exception {
    UserInfoResponse res =
        UserInfoResponse.builder().username("test@gmail.com").lastLogin(Instant.now()).build();
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    given(keycloakService.infoUser(httpServletRequest)).willReturn(res);
    mockMvc
        .perform(get("/api/v1/user/info").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallExistEmail() throws Exception {
    given(keycloakService.checkExistEmail("Test@gmail.com")).willReturn(true);
    mockMvc
        .perform(
            get("/api/v1/user/exist-email")
                .param("email", "Test@gmail.com")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallRoleMapping() throws Exception {
    EventModel model = new EventModel();
    model.setResourcePath("test");
    model.setResourceType("test");
    given(keycloakService.roleMapping(model)).willReturn(true);
    mockMvc
        .perform(
            post("/api/v1/user/role-mapping")
                .content(asJsonString(model))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andDo(print());
  }

  @Test
  void setTimezoneForUser() throws Exception {
    String timezone = "vi";
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(userService.setTimezoneForUser(timezone, httpServletRequest)).thenReturn(true);
    mockMvc
        .perform(post("/api/v1/user/set-timezone").param("timezone", timezone))
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
