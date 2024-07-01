package org.cardanofoundation.authentication.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.request.auth.SignInRequest;
import org.cardanofoundation.authentication.model.request.auth.SignOutRequest;
import org.cardanofoundation.authentication.model.request.auth.SignUpRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.auth.NonceResponse;
import org.cardanofoundation.authentication.model.response.auth.RefreshTokenResponse;
import org.cardanofoundation.authentication.model.response.auth.SignInResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.repository.TokenAuthRepository;
import org.cardanofoundation.authentication.service.AuthenticationService;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
class AuthControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private AuthenticationService authenticationService;

  @MockBean private JwtProvider jwtProvider;

  @MockBean private KeycloakProvider keycloakProvider;

  @MockBean private TokenAuthRepository tokenAuthRepository;

  private final String JWT =
      "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzb3RhdGVrIiwianRpIjoiMiIsImlhdCI6MTY3Mjk5Nzc0MSwiZXhwIjoxNjczMDg0MTQxfQ.B62gXo6iqQfHMT62q17zdhwMF8I77-P6xblKcx7ZI3-gij6YckvFYVVuoIa_qXgTTFnEeRDBQEVo3o20D1w6pffBrgbvxvMbjhOG0ONS9Xs1UQChwQs7v3lxkqoKZ8dNf0Eib43HxLZhBEBIeXa1kln4sS8osWf5iEgno0od7z9KwWK1N2Coj0o-1HE453fFyRveDJgd0DvXohbHADMmjH9t0WkXJwUK26Lv1tkqPlkIzGBPgYnYEIygdayqqt4EtP6CtgI9QOzCYSZUUFzxo-VVDzA0J7DpQbYn8G2PAuAbCXCO6lTkvmXMiyZAoZshqRhBNb7uDI66dwOJLV3NzuunSa8QOO8eNUaDoHHvR_9_J-yHTFBicoM69JHQ7UzJVyFHGmh1M8lHsJ9y6DdAobtBSyJFBhFeDj7S8bgpIvIyNoHDsf24xdlqCngE1qBsxjfp0L_yMPBxsIhW3Juopwe1c6btWTEaRaVaxhKE5yKbRsTtAzDDkdEyg_--9eXH";

  private final String REFRESH_TOKEN = "b2d4e520-4e07-43aa-9a09-f9667f52ce0e";

  private final String ADDRESS_WALLET =
      "stake1u80n7nvm3qlss9ls0krp5xh7sqxlazp8kz6n3fp5sgnul5cnxyg4p";

  private final String NONCE = "9545751481232663225";

  @Test
  void whenCallSignIn() throws Exception {
    SignInRequest request = new SignInRequest();
    request.setType(0);
    request.setEmail("test@gmail.com");
    request.setPassword("@nhPhuc96");
    SignInResponse res =
        SignInResponse.builder()
            .token(JWT)
            .refreshToken(REFRESH_TOKEN)
            .email("test@gmail.com")
            .build();
    given(authenticationService.signIn(request)).willReturn(res);
    mockMvc
        .perform(
            post("/api/v1/auth/sign-in")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallSignUp() throws Exception {
    SignUpRequest request = new SignUpRequest();
    request.setEmail("test@gmail.com");
    request.setPassword("@nhPhuc96");
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    MessageResponse res =
        MessageResponse.builder()
            .code(CommonConstant.CODE_SUCCESS)
            .message(CommonConstant.RESPONSE_SUCCESS)
            .build();
    given(authenticationService.signUp(request, httpServletRequest)).willReturn(res);
    mockMvc
        .perform(
            post("/api/v1/auth/sign-up")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallRefreshToken() throws Exception {
    RefreshTokenResponse res =
        RefreshTokenResponse.builder().accessToken(JWT).refreshToken(REFRESH_TOKEN).build();
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    given(authenticationService.refreshToken(REFRESH_TOKEN, httpServletRequest)).willReturn(res);
    mockMvc
        .perform(
            get("/api/v1/auth/refresh-token")
                .param("refreshJwt", REFRESH_TOKEN)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallSignOut() throws Exception {
    SignOutRequest request = new SignOutRequest();
    request.setAccountId("test@gmail.com");
    request.setRefreshJwt(REFRESH_TOKEN);
    MessageResponse res =
        MessageResponse.builder()
            .code(CommonConstant.CODE_SUCCESS)
            .message(CommonConstant.RESPONSE_SUCCESS)
            .build();
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    given(authenticationService.signOut(request, httpServletRequest)).willReturn(res);
    mockMvc
        .perform(
            post("/api/v1/auth/sign-out")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void whenCallGetNonce() throws Exception {
    NonceResponse res = NonceResponse.builder().nonce(NONCE).build();
    given(authenticationService.findNonceByAddress(ADDRESS_WALLET, "NAMI")).willReturn(res);
    mockMvc
        .perform(
            get("/api/v1/auth/get-nonce")
                .param("address", ADDRESS_WALLET)
                .param("walletName", "NAMI")
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
