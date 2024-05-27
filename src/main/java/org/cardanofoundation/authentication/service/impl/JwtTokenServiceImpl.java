package org.cardanofoundation.authentication.service.impl;

import java.util.List;
import java.util.Set;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import org.cardanofoundation.authentication.exception.BusinessCode;
import org.cardanofoundation.authentication.repository.TokenAuthRepository;
import org.cardanofoundation.authentication.repository.UserRoleRepository;
import org.cardanofoundation.authentication.service.JwtTokenService;
import org.cardanofoundation.explorer.common.entity.enumeration.TokenAuthType;
import org.cardanofoundation.explorer.common.entity.explorer.TokenAuth;
import org.cardanofoundation.explorer.common.entity.explorer.UserRole;
import org.cardanofoundation.explorer.common.exception.BusinessException;

@Service
@RequiredArgsConstructor
@Log4j2
public class JwtTokenServiceImpl implements JwtTokenService {
  private static final String ROLE_PREFIX = "ROLE_";

  private final TokenAuthRepository tokenAuthRepository;
  private final UserRoleRepository userRoleRepository;

  @Override
  @Transactional
  public void saveToken(List<TokenAuth> tokenAuths) {
    tokenAuthRepository.saveAllAndFlush(tokenAuths);
  }

  public TokenAuth findByToken(String token, TokenAuthType tokenAuthType) {
    return tokenAuthRepository
        .findByToken(token, tokenAuthType)
        .orElseThrow(() -> new BusinessException(BusinessCode.TOKEN_NOT_FOUND));
  }

  @Override
  @Transactional
  public void blacklistToken(String token, TokenAuthType tokenAuthType) {
    TokenAuth tokenAuth =
        tokenAuthRepository
            .findByToken(token, tokenAuthType)
            .orElseThrow(() -> new BusinessException(BusinessCode.TOKEN_NOT_FOUND));
    if (!tokenAuth.getBlackList()) {
      tokenAuth.setBlackList(Boolean.TRUE);
      tokenAuthRepository.saveAndFlush(tokenAuth);
    }
  }

  @Override
  @Transactional
  public void saveUserRoleMapping(String userId, String roleId) {
    UserRole userRole = new UserRole(userId, getRoleKeyByRoleId(roleId));
    userRoleRepository.saveAndFlush(userRole);
  }

  public Set<String> findUserByRoleId(String roleId) {
    return userRoleRepository.findByRoleId(getRoleKeyByRoleId(roleId));
  }

  @Override
  @Transactional
  public void deleteTokenByUserId(Set<String> userIds) {
    List<TokenAuth> tokenAuths = tokenAuthRepository.findByUserIdIn(userIds);
    tokenAuths.forEach(e -> e.setBlackList(Boolean.TRUE));
    tokenAuthRepository.saveAllAndFlush(tokenAuths);
  }

  public String getRoleKeyByRoleId(String roleId) {
    return ROLE_PREFIX + roleId;
  }
}
