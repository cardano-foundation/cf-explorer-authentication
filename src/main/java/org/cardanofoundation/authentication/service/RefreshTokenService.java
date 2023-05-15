package org.cardanofoundation.authentication.service;

import org.cardanofoundation.authentication.model.entity.RefreshTokenEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import java.util.Optional;

public interface RefreshTokenService {

  /*
   * @author: phuc.nguyen5
   * @since: 20/10/2022
   * description: get refresh token record by token from db
   * @update:
   */
  Optional<RefreshTokenEntity> findByRefToken(String token);

  /*
   * @author: phuc.nguyen5
   * @since: 20/10/2022
   * description: check expiry time for refresh token record
   * @update:
   */
  RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);

  /*
   * @author: phuc.nguyen5
   * @since: 24/10/2022
   * description: delete refresh token
   * @update:
   */
  void revokeRefreshToken(String token);

  /*
   * @author: phuc.nguyen5
   * @since: 10/1/2022
   * description: create refresh token record
   * @update:
   */
  RefreshTokenEntity addRefreshToken(UserEntity user);

  /*
   * @author: phuc.nguyen5
   * @since: 24/10/2022
   * description: delete refresh token record by username
   * @update:
   */
  void revokeRefreshTokenByUsername(String username);
}
