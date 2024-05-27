package org.cardanofoundation.authentication.service;

import java.util.List;
import java.util.Set;

import org.cardanofoundation.explorer.common.entity.enumeration.TokenAuthType;
import org.cardanofoundation.explorer.common.entity.explorer.TokenAuth;

public interface JwtTokenService {
  void saveToken(List<TokenAuth> tokenAuths);

  public TokenAuth findByToken(String token, TokenAuthType tokenAuthType);

  public void blacklistToken(String token, TokenAuthType tokenAuthType);

  public void saveUserRoleMapping(String userId, String roleId);

  public Set<String> findUserByRoleId(String roleId);

  public void deleteTokenByUserId(Set<String> userIds);
}
