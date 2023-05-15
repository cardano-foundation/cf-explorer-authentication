package org.cardanofoundation.authentication.service.impl;

import org.cardanofoundation.explorer.common.exceptions.TokenRefreshException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.model.entity.RefreshTokenEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.repository.RefreshTokenRepository;
import org.cardanofoundation.authentication.service.RefreshTokenService;
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
