package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.model.entity.RefreshTokenEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.repository.RefreshTokenRepository;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.service.RefreshTokenService;
import com.sotatek.cardanocommonapi.exceptions.TokenRefreshException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

  @Value("${jwt.refreshExpirationMs}")
  private Long refreshExpirationMs;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public Optional<RefreshTokenEntity> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  @Override
  public RefreshTokenEntity createRefreshToken(Long userId, String jwt) {
    Optional<UserEntity> userOpt = userRepository.findById(userId);
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().user(userOpt.orElse(null))
        .expiryDate(Instant.now().plusMillis(refreshExpirationMs))
        .token(UUID.randomUUID().toString()).accessToken(jwt).build();
    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  @Override
  public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(token.getToken(), CommonErrorCode.REFRESH_TOKEN_EXPIRED);
    }
    return token;
  }

  @Override
  public void revokeRefreshToken(String refreshToken) {
    Optional<RefreshTokenEntity> refreshTokenOpt = refreshTokenRepository.findByToken(refreshToken);
    if (refreshTokenOpt.isPresent()) {
      refreshTokenRepository.delete(refreshTokenOpt.get());
    }
  }

  @Override
  public RefreshTokenEntity updateRefreshToken(RefreshTokenEntity token) {
    return refreshTokenRepository.save(token);
  }
}
