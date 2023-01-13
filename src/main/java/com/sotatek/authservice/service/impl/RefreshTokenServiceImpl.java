package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.model.entity.RefreshTokenEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.repository.RefreshTokenRepository;
import com.sotatek.authservice.service.RefreshTokenService;
import com.sotatek.cardanocommonapi.exceptions.TokenRefreshException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RefreshTokenServiceImpl implements RefreshTokenService {

  @Value("${jwt.refreshExpirationMs}")
  private Long refreshExpirationMs;

  private final RefreshTokenRepository refreshTokenRepository;

  @Override
  public Optional<RefreshTokenEntity> findByRefToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  @Override
  public RefreshTokenEntity addRefreshToken(WalletEntity wallet) {
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().wallet(wallet)
        .expiryDate(Instant.now().plusMillis(refreshExpirationMs))
        .token(UUID.randomUUID().toString()).build();
    return refreshTokenRepository.save(refreshToken);
  }

  @Override
  public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(CommonErrorCode.REFRESH_TOKEN_EXPIRED);
    }
    return token;
  }

  @Override
  public void revokeRefreshToken(String token) {
    RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(token)
        .orElseThrow(() -> new TokenRefreshException(CommonErrorCode.REFRESH_TOKEN_IS_NOT_EXIST));
    refreshTokenRepository.delete(refreshToken);
  }

  @Override
  public RefreshTokenEntity addRefreshToken(UserEntity user) {
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().user(user)
        .expiryDate(Instant.now().plusMillis(refreshExpirationMs))
        .token(UUID.randomUUID().toString()).build();
    return refreshTokenRepository.save(refreshToken);
  }

  @Override
  public void revokeRefreshTokenByUsername(String username) {
    List<RefreshTokenEntity> refreshTokenList = refreshTokenRepository.findALlByUsername(username);
    if (Objects.nonNull(refreshTokenList) && !refreshTokenList.isEmpty()) {
      refreshTokenList.forEach(refreshTokenRepository::delete);
    }
  }
}
