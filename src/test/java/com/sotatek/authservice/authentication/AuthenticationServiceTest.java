//package com.sotatek.authservice.authentication;
//
//import com.sotatek.authservice.model.entity.RefreshTokenEntity;
//import com.sotatek.authservice.model.entity.UserEntity;
//import com.sotatek.authservice.model.enums.ERole;
//import com.sotatek.authservice.model.request.auth.RefreshTokenRequest;
//import com.sotatek.authservice.model.request.auth.SignInRequest;
//import com.sotatek.authservice.model.request.auth.SignOutRequest;
//import com.sotatek.authservice.model.request.auth.SignUpRequest;
//import com.sotatek.authservice.model.response.auth.NonceResponse;
//import com.sotatek.authservice.repository.RoleRepository;
//import com.sotatek.authservice.repository.UserRepository;
//import com.sotatek.authservice.service.AuthenticationService;
//import com.sotatek.authservice.service.RefreshTokenService;
//import com.sotatek.cardanocommonapi.exceptions.BusinessException;
//import com.sotatek.cardanocommonapi.exceptions.TokenRefreshException;
//import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
//import java.time.Instant;
//import java.util.Optional;
//import lombok.extern.log4j.Log4j2;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Log4j2
//public class AuthenticationServiceTest {
//
//  @Autowired
//  private AuthenticationService authenticationService;
//
//  @MockBean
//  private UserRepository userRepository;
//
//  @MockBean
//  private RoleRepository roleRepository;
//
//  @MockBean
//  private RefreshTokenService refreshTokenService;
//
//  private final String signature = "84582aa201276761646472657373581de18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909a166686173686564f453393534353735313438313233323636333232355840850ff657e23963414e7c1bf708928dc994ecafea29790089c810af1ac7486aae12a4ed736d16528051aeff1991ee8d2aef19fe3d375f3ad019925ff1530ed608";
//
//  @Test
//  public void whenLogin_GetUserByAddress_UserIsNotExist_ThrowBusinessException() {
//    SignInRequest signInRequest = new SignInRequest();
//    signInRequest.setSignature(signature);
//    Mockito.when(userRepository.findByPublicAddress(
//        "e18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909")).thenReturn(Optional.empty());
//    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
//      authenticationService.signIn(signInRequest);
//    });
//    String expectedErrorCode = CommonErrorCode.USER_IS_NOT_EXIST.getServiceErrorCode();
//    String expectedDesc = CommonErrorCode.USER_IS_NOT_EXIST.getDesc();
//    Assertions.assertEquals(expectedErrorCode, exception.getErrorCode());
//    Assertions.assertEquals(expectedDesc, exception.getErrorMsg());
//  }
//
//  @Test
//  public void whenLogin_GetUserByAddress_NonceHasExpired_ThrowBusinessException() {
//    SignInRequest signInRequest = new SignInRequest();
//    signInRequest.setSignature(signature);
//    UserEntity user = UserEntity.builder().username("test1")
//        .publicAddress("e18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909")
//        .email("test@gmail.com").nonce("8890825581941064700")
//        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
//        .expiryDateNonce(Instant.now().minusSeconds(60)).isDeleted(false).build();
//    Mockito.when(userRepository.findByPublicAddress(
//            "e18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909"))
//        .thenReturn(Optional.of(user));
//    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
//      authenticationService.signIn(signInRequest);
//    });
//    String expectedErrorCode = CommonErrorCode.NONCE_EXPIRED.getServiceErrorCode();
//    String expectedDesc = CommonErrorCode.NONCE_EXPIRED.getDesc();
//    Assertions.assertEquals(expectedErrorCode, exception.getErrorCode());
//    Assertions.assertEquals(expectedDesc, exception.getErrorMsg());
//  }
//
//  @Test
//  public void whenLogin_Authenticate_SignatureInvalid_ThrowValidSignatureException() {
//    SignInRequest signInRequest = new SignInRequest();
//    signInRequest.setSignature(signature);
//    UserEntity user = UserEntity.builder().username("test1")
//        .publicAddress("e18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909")
//        .email("test@gmail.com").nonce("8890825581941064700")
//        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
//        .expiryDateNonce(Instant.now().plusSeconds(180)).isDeleted(false).build();
//    Mockito.when(userRepository.findByPublicAddress(
//            "e18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909"))
//        .thenReturn(Optional.of(user));
//    ValidSignatureException exception = Assertions.assertThrows(ValidSignatureException.class,
//        () -> {
//          authenticationService.signIn(signInRequest);
//        });
//    String expectedErrorCode = CommonErrorCode.SIGNATURE_INVALID.getServiceErrorCode();
//    String expectedDesc = CommonErrorCode.SIGNATURE_INVALID.getDesc();
//    Assertions.assertEquals(expectedErrorCode, exception.getErrCode());
//    Assertions.assertEquals(expectedDesc, exception.getErrMessage());
//  }
//
//  @Test
//  public void whenSignUp_UserIsExistByUsername_ReturnResponse() {
//    SignUpRequest signUpRequest = new SignUpRequest();
//    signUpRequest.setUsername("test1");
//    signUpRequest.setPublicAddress("e18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909");
//    signUpRequest.setEmail("test1@gamil.com");
//    signUpRequest.setPhone("0123456789");
//    Mockito.when(userRepository.existsByUsername("test1")).thenReturn(true);
//    ResponseEntity<NonceResponse> response = authenticationService.signUp(signUpRequest);
//    Assertions.assertEquals("Error: Username is already exist!", response.getBody().getMessage());
//  }
//
//  @Test
//  public void whenSignUp_UserIsExistByPublicAddress_ReturnResponse() {
//    SignUpRequest signUpRequest = new SignUpRequest();
//    signUpRequest.setUsername("test1");
//    signUpRequest.setPublicAddress("e18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909");
//    signUpRequest.setEmail("test1@gamil.com");
//    signUpRequest.setPhone("0123456789");
//    Mockito.when(userRepository.existsByPublicAddress(
//        "e18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909")).thenReturn(true);
//    ResponseEntity<NonceResponse> response = authenticationService.signUp(signUpRequest);
//    Assertions.assertEquals("Error: Address is already in exist!", response.getBody().getMessage());
//  }
//
//  @Test
//  public void whenSignUp_RoleIsNotExist_ThrowRuntimeException() {
//    SignUpRequest signUpRequest = new SignUpRequest();
//    signUpRequest.setUsername("test1");
//    signUpRequest.setPublicAddress("e18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909");
//    signUpRequest.setEmail("test1@gamil.com");
//    signUpRequest.setPhone("0123456789");
//    Mockito.when(userRepository.existsByPublicAddress(
//        "e18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909")).thenReturn(false);
//    Mockito.when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.empty());
//    Mockito.when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.empty());
//    RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
//      authenticationService.signUp(signUpRequest);
//    });
//    Assertions.assertEquals(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc(), exception.getMessage());
//  }
//
//  @Test
//  public void whenRefreshToken_RefreshTokenIsNotExist_ThrowTokenRefreshException() {
//    RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
//    refreshTokenRequest.setRefreshToken("4055eb7c-f98f-4161-8f8a-10537264bb91");
//    Mockito.when(refreshTokenService.findByToken("4055eb7c-f98f-4161-8f8a-10537264bb91"))
//        .thenReturn(Optional.empty());
//    TokenRefreshException exception = Assertions.assertThrows(TokenRefreshException.class, () -> {
//      authenticationService.refreshToken(refreshTokenRequest);
//    });
//    Assertions.assertEquals(CommonErrorCode.REFRESH_TOKEN_IS_NOT_EXIST, exception.getErrorCode());
//  }
//
//  @Test
//  public void whenRefreshToken_AccessTokenIsNull_ThrowBusinessException() {
//    RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
//    refreshTokenRequest.setRefreshToken("4055eb7c-f98f-4161-8f8a-10537264bb91");
//    RefreshTokenEntity refreshToken = new RefreshTokenEntity();
//    refreshToken.setId(1L);
//    refreshToken.setToken("055eb7c-f98f-4161-8f8a-10537264bb91");
//    refreshToken.setAccessToken(null);
//    refreshToken.setExpiryDate(Instant.now());
//    refreshToken.setUser(UserEntity.builder().build());
//    Mockito.when(refreshTokenService.findByToken("4055eb7c-f98f-4161-8f8a-10537264bb91"))
//        .thenReturn(Optional.of(refreshToken));
//    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
//      authenticationService.refreshToken(refreshTokenRequest);
//    });
//    String expectedErrorCode = CommonErrorCode.INVALID_TOKEN.getServiceErrorCode();
//    String expectedDesc = CommonErrorCode.INVALID_TOKEN.getDesc();
//    Assertions.assertEquals(expectedErrorCode, exception.getErrorCode());
//    Assertions.assertEquals(expectedDesc, exception.getErrorMsg());
//  }
//
//  @Test
//  public void whenLogout_AccessTokenIsNull_ThrowBusinessException() {
//    SignOutRequest signOutRequest = new SignOutRequest();
//    signOutRequest.setAccessToken(null);
//    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
//      authenticationService.signOut(signOutRequest);
//    });
//    String expectedErrorCode = CommonErrorCode.INVALID_TOKEN.getServiceErrorCode();
//    String expectedDesc = CommonErrorCode.INVALID_TOKEN.getDesc();
//    Assertions.assertEquals(expectedErrorCode, exception.getErrorCode());
//    Assertions.assertEquals(expectedDesc, exception.getErrorMsg());
//  }
//
//  @Test
//  public void whenLogout_SaveHistoryUserIsNull_ThrowBusinessException() {
//    SignOutRequest signOutRequest = new SignOutRequest();
//    signOutRequest.setAccessToken("eyJhbG1234567890adam");
//    signOutRequest.setRefreshToken("4055eb7c-f98f-4161-8f8a-1053726bb91");
//    signOutRequest.setUsername("test1");
//    Mockito.when(userRepository.findByUsername("test1")).thenReturn(Optional.empty());
//    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
//      authenticationService.signOut(signOutRequest);
//    });
//    String expectedErrorCode = CommonErrorCode.USER_IS_NOT_EXIST.getServiceErrorCode();
//    String expectedDesc = CommonErrorCode.USER_IS_NOT_EXIST.getDesc();
//    Assertions.assertEquals(expectedErrorCode, exception.getErrorCode());
//    Assertions.assertEquals(expectedDesc, exception.getErrorMsg());
//  }
//}