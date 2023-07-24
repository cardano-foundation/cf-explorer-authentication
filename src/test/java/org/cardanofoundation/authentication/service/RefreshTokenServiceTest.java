package org.cardanofoundation.authentication.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.cardanofoundation.authentication.model.entity.RefreshTokenEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.repository.RefreshTokenRepository;
import org.cardanofoundation.authentication.service.impl.RefreshTokenServiceImpl;
import org.cardanofoundation.explorer.common.exceptions.TokenRefreshException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RefreshTokenServiceTest {

  @InjectMocks
  private RefreshTokenServiceImpl refreshTokenService;

  @Mock
  private RefreshTokenRepository refreshTokenRepository;

  private final String REFRESH_TOKEN = "b2d4e520-4e07-43aa-9a09-f9667f52ce0e";

  @BeforeEach
  public void setUp() {
    ReflectionTestUtils.setField(refreshTokenService, "refreshExpirationMs", 360000L);
  }

  @Test
  void whenFindByRefToken_IsNotExist_returnEmpty() {
    when(refreshTokenRepository.findByToken(REFRESH_TOKEN)).thenReturn(Optional.empty());
    Optional<RefreshTokenEntity> response = refreshTokenService.findByRefToken(REFRESH_TOKEN);
    Assertions.assertFalse(response.isPresent());
  }

  @Test
  void whenFindByRefToken_IsExist_returnResponse() {
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().token(REFRESH_TOKEN).expiryDate(
        Instant.now().plusMillis(3600)).build();
    when(refreshTokenRepository.findByToken(REFRESH_TOKEN)).thenReturn(Optional.of(refreshToken));
    Optional<RefreshTokenEntity> response = refreshTokenService.findByRefToken(REFRESH_TOKEN);
    Assertions.assertTrue(response.isPresent());
  }

  @Test
  void whenVerifyExpiration_tokenIsExpired_throwException() {
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().token(REFRESH_TOKEN).expiryDate(
        Instant.now().minusSeconds(3600)).build();
    doNothing().when(refreshTokenRepository).delete(refreshToken);
    TokenRefreshException exception = Assertions.assertThrows(TokenRefreshException.class, () -> {
      refreshTokenService.verifyExpiration(refreshToken);
    });
    String expectedCode = CommonErrorCode.REFRESH_TOKEN_EXPIRED.getServiceErrorCode();
    String actualCode = exception.getErrorCode().getServiceErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenRevokeRefreshToken_tokenIsNotExist_throwException() {
    when(refreshTokenRepository.findByToken(REFRESH_TOKEN)).thenReturn(Optional.empty());
    TokenRefreshException exception = Assertions.assertThrows(TokenRefreshException.class, () -> {
      refreshTokenService.revokeRefreshToken(REFRESH_TOKEN);
    });
    String expectedCode = CommonErrorCode.REFRESH_TOKEN_IS_NOT_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode().getServiceErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenRevokeRefreshToken_tokenIsExist_runSuccess() {
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().token(REFRESH_TOKEN).expiryDate(
        Instant.now().minusSeconds(3600)).build();
    when(refreshTokenRepository.findByToken(REFRESH_TOKEN)).thenReturn(Optional.of(refreshToken));
    doNothing().when(refreshTokenRepository).delete(refreshToken);
    refreshTokenService.revokeRefreshToken(REFRESH_TOKEN);
  }

  @Test
  void whenAddRefreshToken_returnResponse() {
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().user(user)
        .expiryDate(Instant.now())
        .token(UUID.randomUUID().toString()).build();
    refreshToken.setUser(user);
    when(refreshTokenRepository.save(any())).thenReturn(refreshToken);
    RefreshTokenEntity response = refreshTokenService.addRefreshToken(user);
    Assertions.assertNotNull(response);
  }
}
