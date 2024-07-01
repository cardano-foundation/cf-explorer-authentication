package org.cardanofoundation.authentication.service;

import java.util.List;
import java.util.Set;

import org.cardanofoundation.explorer.common.entity.enumeration.TokenAuthType;
import org.cardanofoundation.explorer.common.entity.explorer.TokenAuth;

public interface JwtTokenService {
  void saveToken(List<TokenAuth> tokenAuths);

  Boolean isBlacklistToken(String token, TokenAuthType tokenAuthType);

  void blacklistToken(String token, TokenAuthType tokenAuthType);

  void saveUserRoleMapping(String userId, String roleId);

  Set<String> findUserByRoleId(String roleId);

  void blacklistTokenByUserId(Set<String> userIds);
}
