package org.cardanofoundation.authentication.authentication;

import static org.mockito.ArgumentMatchers.any;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import org.cardanofoundation.authentication.model.entity.RefreshTokenEntity;
import org.cardanofoundation.authentication.model.entity.RoleEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.entity.UserHistoryEntity;
import org.cardanofoundation.authentication.model.entity.WalletEntity;
import org.cardanofoundation.authentication.model.entity.security.UserDetailsImpl;
import org.cardanofoundation.authentication.model.enums.ERole;
import org.cardanofoundation.authentication.model.enums.EStatus;
import org.cardanofoundation.authentication.model.request.auth.SignInRequest;
import org.cardanofoundation.authentication.model.request.auth.SignUpRequest;
import org.cardanofoundation.authentication.model.response.auth.SignInResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.RedisProvider;
import org.cardanofoundation.authentication.repository.UserHistoryRepository;
import org.cardanofoundation.authentication.repository.WalletRepository;
import org.cardanofoundation.authentication.service.RefreshTokenService;
import org.cardanofoundation.authentication.service.UserService;
import org.cardanofoundation.authentication.service.WalletService;
import org.cardanofoundation.authentication.service.impl.AuthenticationServiceImpl;
import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.IgnoreRollbackException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

  @InjectMocks
  private AuthenticationServiceImpl authenticationService;

  @Mock
  private UserService userService;

  @Mock
  private WalletService walletService;

  @Mock
  private UserHistoryRepository userHistoryRepository;

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

  @Before
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  private final String SIGNATURE_TEST = "84582aa201276761646472657373581de18a18031ff10e307f9ceff8929608c5f58bdba08304e380c034f85909a166686173686564f453393534353735313438313233323636333232355840850ff657e23963414e7c1bf708928dc994ecafea29790089c810af1ac7486aae12a4ed736d16528051aeff1991ee8d2aef19fe3d375f3ad019925ff1530ed608";

  private final String ADDRESS_WALLET = "stake1u80n7nvm3qlss9ls0krp5xh7sqxlazp8kz6n3fp5sgnul5cnxyg4p";

  private final String NONCE = "9545751481232663225";

  private final String JWT = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzb3RhdGVrIiwianRpIjoiMiIsImlhdCI6MTY3Mjk5Nzc0MSwiZXhwIjoxNjczMDg0MTQxfQ.B62gXo6iqQfHMT62q17zdhwMF8I77-P6xblKcx7ZI3-gij6YckvFYVVuoIa_qXgTTFnEeRDBQEVo3o20D1w6pffBrgbvxvMbjhOG0ONS9Xs1UQChwQs7v3lxkqoKZ8dNf0Eib43HxLZhBEBIeXa1kln4sS8osWf5iEgno0od7z9KwWK1N2Coj0o-1HE453fFyRveDJgd0DvXohbHADMmjH9t0WkXJwUK26Lv1tkqPlkIzGBPgYnYEIygdayqqt4EtP6CtgI9QOzCYSZUUFzxo-VVDzA0J7DpQbYn8G2PAuAbCXCO6lTkvmXMiyZAoZshqRhBNb7uDI66dwOJLV3NzuunSa8QOO8eNUaDoHHvR_9_J-yHTFBicoM69JHQ7UzJVyFHGmh1M8lHsJ9y6DdAobtBSyJFBhFeDj7S8bgpIvIyNoHDsf24xdlqCngE1qBsxjfp0L_yMPBxsIhW3Juopwe1c6btWTEaRaVaxhKE5yKbRsTtAzDDkdEyg_--9eXH";

  private final String REFRESH_TOKEN = "b2d4e520-4e07-43aa-9a09-f9667f52ce0e";

  private final String EMAIL = "test.30.04@gmail.com";

  private final String PASSWORD = "password";

  @Test
  public void whenLoginUsingWallet_SignatureInValid_ThrowException() {
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
  public void whenLoginUsingWallet_AddressIsNotExist_ThrowException() {
    String addressWallet = "Test123456789";
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setSignature(SIGNATURE_TEST);
    signInRequest.setAddress(addressWallet);
    signInRequest.setType(1);
    Mockito.when(walletRepository.findWalletByAddress(addressWallet)).thenReturn(Optional.empty());
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      authenticationService.signIn(signInRequest);
    });
    String expectedCode = CommonErrorCode.WALLET_IS_NOT_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  public void whenLoginUsingWallet_NonceIsExpired_ThrowException() {
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setSignature(SIGNATURE_TEST);
    signInRequest.setAddress(ADDRESS_WALLET);
    signInRequest.setType(1);
    WalletEntity wallet = WalletEntity.builder().address(ADDRESS_WALLET)
        .expiryDateNonce(Instant.now().minusSeconds(3600)).build();
    Mockito.when(walletRepository.findWalletByAddress(ADDRESS_WALLET))
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
  public void whenLoginUsingWallet_AuthenticateFail_ThrowException() {
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setSignature(SIGNATURE_TEST);
    signInRequest.setAddress(ADDRESS_WALLET);
    signInRequest.setType(1);
    WalletEntity wallet = WalletEntity.builder().address(ADDRESS_WALLET)
        .expiryDateNonce(Instant.now().plusSeconds(3600)).build();
    Mockito.when(walletRepository.findWalletByAddress(ADDRESS_WALLET))
        .thenReturn(Optional.of(wallet));
    Mockito.when(authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(ADDRESS_WALLET, NONCE)))
        .thenThrow(new BusinessException(CommonErrorCode.SIGNATURE_INVALID));
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      authenticationService.signIn(signInRequest);
    });
    String expectedCode = CommonErrorCode.SIGNATURE_INVALID.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  public void whenLoginUsingWallet_AuthenticateSuccess_returnResponse() {
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
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
    Mockito.when(walletRepository.findWalletByAddress(ADDRESS_WALLET))
        .thenReturn(Optional.of(wallet));
    Mockito.when(authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(ADDRESS_WALLET, NONCE))).thenReturn(authentication);
    Mockito.when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    Mockito.when(jwtProvider.generateJwtToken(authentication, ADDRESS_WALLET)).thenReturn(JWT);
    Mockito.when(refreshTokenService.addRefreshToken(user))
        .thenReturn(RefreshTokenEntity.builder().token(REFRESH_TOKEN).build());
    Mockito.when(userHistoryRepository.save(any())).thenReturn(UserHistoryEntity.builder().build());
    SignInResponse response = authenticationService.signIn(signInRequest);
    Assertions.assertNotNull(response);
  }

  @Test
  public void whenLoginUsingEmal_UserIsNotExist_ThrowException() {
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setEmail(EMAIL);
    signInRequest.setPassword(PASSWORD);
    signInRequest.setType(0);
    Mockito.when(authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD)))
        .thenThrow(new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      authenticationService.signIn(signInRequest);
    });
    String expectedCode = CommonErrorCode.USER_IS_NOT_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  public void whenLoginUsingEmal_EmalOrPasswordInValid_ThrowException() {
    SignInRequest signInRequest = new SignInRequest();
    signInRequest.setEmail(EMAIL);
    signInRequest.setPassword(PASSWORD);
    signInRequest.setType(0);
    Mockito.when(authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD)))
        .thenThrow(new BusinessException(CommonErrorCode.USERNAME_OR_PASSWORD_INVALID));
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      authenticationService.signIn(signInRequest);
    });
    String expectedCode = CommonErrorCode.USERNAME_OR_PASSWORD_INVALID.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  public void whenLoginUsingEmal_AuthenticateSuccess_ThrowException() {
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
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
    Mockito.when(authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD))).thenReturn(authentication);
    Mockito.when(userService.findByAccountId(EMAIL)).thenReturn(user);
    Mockito.when(jwtProvider.generateJwtToken(authentication, EMAIL)).thenReturn(JWT);
    Mockito.when(refreshTokenService.addRefreshToken(user))
        .thenReturn(RefreshTokenEntity.builder().token(REFRESH_TOKEN).build());
    Mockito.when(userHistoryRepository.save(any())).thenReturn(UserHistoryEntity.builder().build());
    SignInResponse response = authenticationService.signIn(signInRequest);
    Assertions.assertNotNull(response);
  }

  @Test
  public void whenSignUp_EmailIsExist_ThrowException() {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setEmail("test5.6@gmail.com");
    signUpRequest.setPassword(PASSWORD);
    Mockito.when(userService.checkExistEmailAndStatus("test5.6@gmail.com", EStatus.ACTIVE))
        .thenReturn(true);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      authenticationService.signUp(signUpRequest);
    });
    String expectedCode = CommonErrorCode.EMAIL_IS_ALREADY_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  public void whenRefreshToken_RefreshTokenIsNotExist_ThrowException() {
    Mockito.when(jwtProvider.parseJwt(any())).thenReturn(JWT);
    Mockito.when(refreshTokenService.findByRefToken(REFRESH_TOKEN)).thenReturn(Optional.empty());
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      authenticationService.refreshToken(REFRESH_TOKEN, any());
    });
    String expectedCode = CommonErrorCode.UNKNOWN_ERROR.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  public void whenRefreshToken_RefreshTokenIsExpired_ThrowException() {
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
        .expiryDate(Instant.now().minusSeconds(3600)).token(REFRESH_TOKEN).build();
    Mockito.when(jwtProvider.parseJwt(any())).thenReturn(JWT);
    Mockito.when(refreshTokenService.findByRefToken(REFRESH_TOKEN))
        .thenReturn(Optional.of(refreshToken));
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      authenticationService.refreshToken(REFRESH_TOKEN, any());
    });
    String expectedCode = CommonErrorCode.UNKNOWN_ERROR.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }
}