package org.cardanofoundation.authentication.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.exception.AuthenticateException;
import org.cardanofoundation.authentication.model.entity.RefreshTokenEntity;
import org.cardanofoundation.authentication.model.entity.RoleEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.entity.WalletEntity;
import org.cardanofoundation.authentication.model.entity.security.UserDetailsImpl;
import org.cardanofoundation.authentication.model.enums.ERole;
import org.cardanofoundation.authentication.model.enums.EStatus;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.model.request.auth.SignInRequest;
import org.cardanofoundation.authentication.model.request.auth.SignOutRequest;
import org.cardanofoundation.authentication.model.request.auth.SignUpRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.auth.NonceResponse;
import org.cardanofoundation.authentication.model.response.auth.RefreshTokenResponse;
import org.cardanofoundation.authentication.model.response.auth.SignInResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.MailProvider;
import org.cardanofoundation.authentication.provider.RedisProvider;
import org.cardanofoundation.authentication.repository.RefreshTokenRepository;
import org.cardanofoundation.authentication.repository.UserRepository;
import org.cardanofoundation.authentication.repository.WalletRepository;
import org.cardanofoundation.authentication.service.impl.AuthenticationServiceImpl;
import org.cardanofoundation.authentication.thread.MailHandler;
import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.IgnoreRollbackException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthenticationServiceTest {

  @InjectMocks
  private AuthenticationServiceImpl authenticationService;

  @Mock
  private UserService userService;

  @Mock
  private WalletService walletService;

  @Mock
  private UserHistoryService userHistoryService;

  @Mock
  private WalletRepository walletRepository;

  @Mock
  private RefreshTokenService refreshTokenService;

  @Mock
  private JwtProvider jwtProvider;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private RedisProvider redisProvider;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder encoder;

  @Mock
  private ThreadPoolExecutor sendMailExecutor;

  @Mock
  private MailProvider mailProvider;

  @Mock
  private RefreshTokenRepository refreshTokenRepository;

  @Mock
  private LocaleResolver localeResolver;

  private final String SIGNATURE_TEST = "84582aa201276761646472657373581de18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909a166686173686564f453393534353735313438313233323636333232355840850ff657e23963414e7c1bf708928dc994ecafea29790089c810af1ac7486aae12a4ed736d16528051aeff1991ee8d2aef19fe3d375f3ad019925ff1530ed608";

  private final String ADDRESS_WALLET = "stake1u80n7nvm3qlss9ls0krp5xh7sqxlazp8kz6n3fp5sgnul5cnxyg4p";

  private final String NONCE = "9545751481232663225";

  private final String JWT = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzb3RhdGVrIiwianRpIjoiMiIsImlhdCI6MTY3Mjk5Nzc0MSwiZXhwIjoxNjczMDg0MTQxfQ.B62gXo6iqQfHMT62q17zdhwMF8I77-P6xblKcx7ZI3-gij6YckvFYVVuoIa_qXgTTFnEeRDBQEVo3o20D1w6pffBrgbvxvMbjhOG0ONS9Xs1UQChwQs7v3lxkqoKZ8dNf0Eib43HxLZhBEBIeXa1kln4sS8osWf5iEgno0od7z9KwWK1N2Coj0o-1HE453fFyRveDJgd0DvXohbHADMmjH9t0WkXJwUK26Lv1tkqPlkIzGBPgYnYEIygdayqqt4EtP6CtgI9QOzCYSZUUFzxo-VVDzA0J7DpQbYn8G2PAuAbCXCO6lTkvmXMiyZAoZshqRhBNb7uDI66dwOJLV3NzuunSa8QOO8eNUaDoHHvR_9_J-yHTFBicoM69JHQ7UzJVyFHGmh1M8lHsJ9y6DdAobtBSyJFBhFeDj7S8bgpIvIyNoHDsf24xdlqCngE1qBsxjfp0L_yMPBxsIhW3Juopwe1c6btWTEaRaVaxhKE5yKbRsTtAzDDkdEyg_--9eXH";

  private final String REFRESH_TOKEN = "b2d4e520-4e07-43aa-9a09-f9667f52ce0e";

  private final String EMAIL = "test.30.04@gmail.com";

  private final String PASSWORD = "password";

  @Test
  void whenLoginUsingWallet_SignatureInValid_ThrowException() {
    String signatureInValid = "Test123456789";
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setSignature(signatureInValid);
    signInRequest.setType(1);
    Exception exception = Assertions.assertThrows(Exception.class, () -> {
      authenticationService.signIn(signInRequest);
    });
    String expectedMessage = "Invalid hexadecimal";
    String actualMessage = exception.getMessage();
    Assertions.assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void whenLoginUsingWallet_AddressIsNotExist_ThrowException() {
    String addressWallet = "Test123456789";
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setSignature(SIGNATURE_TEST);
    signInRequest.setAddress(addressWallet);
    signInRequest.setType(1);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      authenticationService.signIn(signInRequest);
    });
    String expectedCode = CommonErrorCode.WALLET_IS_NOT_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenLoginUsingWallet_NonceIsExpired_ThrowException() {
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setSignature(SIGNATURE_TEST);
    signInRequest.setAddress(ADDRESS_WALLET);
    signInRequest.setType(1);
    WalletEntity wallet = WalletEntity.builder().address(ADDRESS_WALLET)
        .expiryDateNonce(Instant.now().minusSeconds(3600)).build();
    when(walletRepository.findWalletByAddress(ADDRESS_WALLET))
        .thenReturn(Optional.of(wallet));
    IgnoreRollbackException exception = Assertions.assertThrows(IgnoreRollbackException.class,
        () -> {
          authenticationService.signIn(signInRequest);
        });
    String expectedCode = CommonErrorCode.NONCE_EXPIRED.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenLoginUsingWallet_AuthenticateFail_ThrowException() {
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setSignature(SIGNATURE_TEST);
    signInRequest.setAddress(ADDRESS_WALLET);
    signInRequest.setType(1);
    WalletEntity wallet = WalletEntity.builder().address(ADDRESS_WALLET)
        .expiryDateNonce(Instant.now().plusSeconds(3600)).build();
    when(walletRepository.findWalletByAddress(ADDRESS_WALLET))
        .thenReturn(Optional.of(wallet));
    when(authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(ADDRESS_WALLET, NONCE)))
        .thenThrow(new AuthenticateException("Test"));
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      authenticationService.signIn(signInRequest);
    });
    String expectedCode = CommonErrorCode.SIGNATURE_INVALID.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenLoginUsingWallet_AuthenticateSuccess_returnResponse() {
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setSignature(SIGNATURE_TEST);
    signInRequest.setAddress(ADDRESS_WALLET);
    signInRequest.setType(1);
    WalletEntity wallet = WalletEntity.builder().address(ADDRESS_WALLET)
        .expiryDateNonce(Instant.now().plusSeconds(3600)).build();
    Authentication authentication = Mockito.mock(Authentication.class);
    RoleEntity role = new RoleEntity();
    role.setName(ERole.ROLE_USER);
    UserEntity user = UserEntity.builder()
        .email(EMAIL).roles(Set.of(role)).isDeleted(false).build();
    UserDetailsImpl userDetails = UserDetailsImpl.build(user, ADDRESS_WALLET, NONCE);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(walletRepository.findWalletByAddress(ADDRESS_WALLET))
        .thenReturn(Optional.of(wallet));
    when(authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(ADDRESS_WALLET, NONCE))).thenReturn(authentication);
    when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    when(jwtProvider.generateJwtToken(authentication, ADDRESS_WALLET)).thenReturn(JWT);
    when(refreshTokenService.addRefreshToken(user))
        .thenReturn(RefreshTokenEntity.builder().token(REFRESH_TOKEN).build());
    doNothing().when(userHistoryService).saveUserHistory(any(), any(), any());
    SignInResponse response = authenticationService.signIn(signInRequest);
    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.getToken());
  }

  @Test
  void whenLoginUsingEmail_EmailOrPasswordInValid_ThrowException() {
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setEmail(EMAIL);
    signInRequest.setPassword(PASSWORD);
    signInRequest.setType(0);
    when(authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD)))
        .thenThrow(new AuthenticateException("Email or password invalid"));
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      authenticationService.signIn(signInRequest);
    });
    String expectedCode = CommonErrorCode.USERNAME_OR_PASSWORD_INVALID.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenLoginUsingEmail_AuthenticateSuccess_ReturnResponse() {
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setEmail(EMAIL);
    signInRequest.setPassword(PASSWORD);
    signInRequest.setType(0);
    Authentication authentication = Mockito.mock(Authentication.class);
    RoleEntity role = new RoleEntity();
    role.setName(ERole.ROLE_USER);
    UserEntity user = UserEntity.builder()
        .email("test5.6@gmail.com").roles(Set.of(role)).isDeleted(false).build();
    UserDetailsImpl userDetails = UserDetailsImpl.build(user, EMAIL, PASSWORD);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD))).thenReturn(authentication);
    when(userService.findByAccountId(EMAIL)).thenReturn(user);
    when(jwtProvider.generateJwtToken(authentication, EMAIL)).thenReturn(JWT);
    when(refreshTokenService.addRefreshToken(user))
        .thenReturn(RefreshTokenEntity.builder().token(REFRESH_TOKEN).build());
    doNothing().when(userHistoryService).saveUserHistory(any(), any(), any());
    SignInResponse response = authenticationService.signIn(signInRequest);
    Assertions.assertNotNull(response);
  }

  @Test
  void whenSignUp_EmailIsExist_ThrowException() {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setEmail("test5.6@gmail.com");
    signUpRequest.setPassword(PASSWORD);
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(userService.checkExistEmailAndStatus("test5.6@gmail.com", EStatus.ACTIVE))
        .thenReturn(true);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      authenticationService.signUp(signUpRequest, httpServletRequest);
    });
    String expectedCode = CommonErrorCode.EMAIL_IS_ALREADY_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenSignUp_EmailIsNotExist_returnResponseSuccess() {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setEmail("test5.6@gmail.com");
    signUpRequest.setPassword(PASSWORD);
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    UserEntity user = UserEntity.builder().email("test5.6@gmail.com").build();
    when(userService.checkExistEmailAndStatus("test5.6@gmail.com", EStatus.ACTIVE))
        .thenReturn(false);
    when(encoder.encode(PASSWORD)).thenReturn(
        "$2a$10$qbpa.SGNi0y3Chv2ENMCT.5aTVwgm8oZesdt4xsDuyp2WztqHaY8u");
    when(userRepository.findByEmailAndStatus("test5.6@gmail.com", EStatus.PENDING)).thenReturn(
        Optional.empty());
    when(userService.saveUser(signUpRequest)).thenReturn(user);
    when(jwtProvider.generateCodeForVerify("test5.6@gmail.com")).thenReturn(JWT);
    doNothing().when(sendMailExecutor)
        .execute(new MailHandler(mailProvider, user, EUserAction.CREATED, any(), JWT));
    MessageResponse response = authenticationService.signUp(signUpRequest, httpServletRequest);
    String expectedCode = CommonConstant.CODE_SUCCESS;
    Assertions.assertEquals(expectedCode, response.getCode());
  }

  @Test
  void whenSignUp_EmailIsNotExistWithStatusPending_returnResponseSuccess() {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setEmail("test5.6@gmail.com");
    signUpRequest.setPassword(PASSWORD);
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    UserEntity user = UserEntity.builder().email("test5.6@gmail.com").status(EStatus.PENDING)
        .password(PASSWORD).build();
    when(userService.checkExistEmailAndStatus("test5.6@gmail.com", EStatus.ACTIVE))
        .thenReturn(false);
    when(encoder.encode(PASSWORD)).thenReturn(
        "$2a$10$qbpa.SGNi0y3Chv2ENMCT.5aTVwgm8oZesdt4xsDuyp2WztqHaY8u");
    when(userRepository.findByEmailAndStatus("test5.6@gmail.com", EStatus.PENDING)).thenReturn(
        Optional.of(user));
    when(userRepository.save(user)).thenReturn(user);
    when(jwtProvider.generateCodeForVerify("test5.6@gmail.com")).thenReturn(JWT);
    doNothing().when(sendMailExecutor)
        .execute(new MailHandler(mailProvider, user, EUserAction.CREATED, any(), JWT));
    MessageResponse response = authenticationService.signUp(signUpRequest, httpServletRequest);
    String expectedCode = CommonConstant.CODE_SUCCESS;
    Assertions.assertEquals(expectedCode, response.getCode());
  }

  @Test
  void whenRefreshToken_RefreshTokenIsNotExist_ThrowException() {
    when(jwtProvider.parseJwt(any())).thenReturn(JWT);
    when(refreshTokenService.findByRefToken(REFRESH_TOKEN)).thenReturn(Optional.empty());
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      authenticationService.refreshToken(REFRESH_TOKEN, any());
    });
    String expectedCode = CommonErrorCode.UNKNOWN_ERROR.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenRefreshToken_RefreshTokenIsExpired_ThrowException() {
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
        .expiryDate(Instant.now().minusSeconds(3600)).token(REFRESH_TOKEN).build();
    when(jwtProvider.parseJwt(any())).thenReturn(JWT);
    when(refreshTokenService.findByRefToken(REFRESH_TOKEN)).thenReturn(Optional.of(refreshToken));
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      authenticationService.refreshToken(REFRESH_TOKEN, any());
    });
    String expectedCode = CommonErrorCode.UNKNOWN_ERROR.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenRefreshToken_RefreshTokenIsNotExpired_returnResponseSuccess() {
    UserEntity user = UserEntity.builder().email("test5.6@gmail.com").status(EStatus.ACTIVE)
        .password(PASSWORD).build();
    user.setId(1L);
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().user(user)
        .expiryDate(Instant.now().plusMillis(3600)).token(REFRESH_TOKEN).build();
    when(jwtProvider.parseJwt(any())).thenReturn(JWT);
    when(refreshTokenService.findByRefToken(REFRESH_TOKEN)).thenReturn(Optional.of(refreshToken));
    when(refreshTokenService.verifyExpiration(refreshToken)).thenReturn(refreshToken);
    when(walletRepository.findAddressByUserId(1L)).thenReturn(ADDRESS_WALLET);
    doNothing().when(redisProvider).blacklistJwt(JWT, ADDRESS_WALLET);
    when(jwtProvider.generateJwtToken(user, ADDRESS_WALLET)).thenReturn(JWT);
    RefreshTokenResponse response = authenticationService.refreshToken(REFRESH_TOKEN, any());
    Assertions.assertEquals(JWT, response.getAccessToken());
  }

  @Test
  void whenSignOut_returnResponseSuccess() {
    SignOutRequest request = new SignOutRequest();
    request.setRefreshJwt(REFRESH_TOKEN);
    request.setAccountId(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().email("test5.6@gmail.com").status(EStatus.ACTIVE)
        .password(PASSWORD).build();
    user.setId(1L);
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().user(user)
        .expiryDate(Instant.now().plusMillis(3600)).token(REFRESH_TOKEN).build();
    when(jwtProvider.parseJwt(any())).thenReturn(JWT);
    when(refreshTokenRepository.findByToken(REFRESH_TOKEN)).thenReturn(Optional.of(refreshToken));
    doNothing().when(redisProvider).blacklistJwt(JWT, ADDRESS_WALLET);
    MessageResponse response = authenticationService.signOut(request, any());
    String expectedCode = CommonConstant.CODE_SUCCESS;
    Assertions.assertEquals(expectedCode, response.getCode());
  }

  @Test
  void whenFindNonce_WalletIsNotExist_returnResponse() {
    UserEntity user = UserEntity.builder().build();
    WalletEntity wallet = WalletEntity.builder().user(user).address(ADDRESS_WALLET)
        .walletName("NAMI").build();
    when(walletRepository.findWalletByAddress(ADDRESS_WALLET)).thenReturn(Optional.empty());
    when(userService.saveUser(ADDRESS_WALLET)).thenReturn(user);
    when(walletService.saveWallet(ADDRESS_WALLET, user, "NAMI")).thenReturn(wallet);
    NonceResponse response = authenticationService.findNonceByAddress(ADDRESS_WALLET, "NAMI");
    String expectedCode = CommonConstant.CODE_FAILURE;
    Assertions.assertEquals(expectedCode, response.getMessage());
  }

  @Test
  void whenFindNonce_WalletIsExist_returnResponse() {
    UserEntity user = UserEntity.builder().build();
    WalletEntity wallet = WalletEntity.builder().user(user).address(ADDRESS_WALLET)
        .expiryDateNonce(Instant.now().plusSeconds(60)).walletName("NAMI").build();
    when(walletRepository.findWalletByAddress(ADDRESS_WALLET)).thenReturn(Optional.of(wallet));
    NonceResponse response = authenticationService.findNonceByAddress(ADDRESS_WALLET, "NAMI");
    String expectedCode = CommonConstant.CODE_SUCCESS;
    Assertions.assertEquals(expectedCode, response.getMessage());
  }
}