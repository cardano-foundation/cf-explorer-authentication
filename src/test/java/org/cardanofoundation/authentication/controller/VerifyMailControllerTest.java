package org.cardanofoundation.authentication.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.request.auth.ResetPasswordRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.provider.RedisProvider;
import org.cardanofoundation.authentication.service.VerifyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VerifyMailController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
class VerifyMailControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private VerifyService verifyService;

  @MockBean
  private KeycloakProvider keycloakProvider;

  @MockBean
  private JwtProvider jwtProvider;

  @MockBean
  private RedisProvider redisProvider;

  private final String CODE = "CodeVerifyMail123456";

  @Test
  void whenCallActive() throws Exception {
    MessageResponse res = MessageResponse.builder().code(CommonConstant.CODE_SUCCESS)
        .message(CommonConstant.RESPONSE_SUCCESS).build();
    given(verifyService.checkVerifySignUpByEmail(CODE)).willReturn(res);
    mockMvc.perform(get("/api/v1/verify/active")
            .param("code", CODE)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallForgotPassword() throws Exception {
    MessageResponse res = MessageResponse.builder().code(CommonConstant.CODE_SUCCESS)
        .message(CommonConstant.RESPONSE_SUCCESS).build();
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    given(verifyService.forgotPassword("Test@gmail.com", httpServletRequest)).willReturn(res);
    mockMvc.perform(get("/api/v1/verify/forgot-password")
            .param("email", "Test@gmail.com")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallResetPassword() throws Exception {
    ResetPasswordRequest request = new ResetPasswordRequest();
    request.setPassword("@nhPhuc96");
    request.setCode(CODE);
    MessageResponse res = MessageResponse.builder().code(CommonConstant.CODE_SUCCESS)
        .message(CommonConstant.RESPONSE_SUCCESS).build();
    given(verifyService.resetPassword(request)).willReturn(res);
    mockMvc.perform(put("/api/v1/verify/reset-password")
            .content(asJsonString(request))
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
