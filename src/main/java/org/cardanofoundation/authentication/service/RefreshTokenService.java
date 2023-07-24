package org.cardanofoundation.authentication.service;

import java.util.Optional;
import org.cardanofoundation.authentication.model.entity.RefreshTokenEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;

public interface RefreshTokenService {

  /*
   * @since: 20/10/2022
   * description: get refresh token record by token from db
   * @update:
   */
  Optional<RefreshTokenEntity> findByRefToken(String token);

  /*
   * @since: 20/10/2022
   * description: check expiry time for refresh token record
   * @update:
   */
  RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);

  /*
   * @since: 24/10/2022
   * description: delete refresh token
   * @update:
   */
  void revokeRefreshToken(String token);

  /*
   * @since: 10/1/2022
   * description: create refresh token record
   * @update:
   */
  RefreshTokenEntity addRefreshToken(UserEntity user);
}
