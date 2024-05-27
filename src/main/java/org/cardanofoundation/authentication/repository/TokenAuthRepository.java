package org.cardanofoundation.authentication.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.cardanofoundation.explorer.common.entity.enumeration.TokenAuthType;
import org.cardanofoundation.explorer.common.entity.explorer.TokenAuth;

@Repository
public interface TokenAuthRepository extends JpaRepository<TokenAuth, Long> {
  @Query(
      value =
          """
   SELECT ta FROM TokenAuth ta WHERE ta.token = :token AND ta.tokenAuthType = :tokenAuthType
""")
  Optional<TokenAuth> findByToken(
      @Param("token") String token, @Param("tokenAuthType") TokenAuthType tokenAuthType);

  @Query(
      value =
          """
   SELECT ta.blackList FROM TokenAuth ta WHERE ta.token = :token AND ta.tokenAuthType = :tokenAuthType ORDER BY ta.id DESC LIMIT 1
""")
  Boolean isBlacklistToken(
      @Param("token") String token, @Param("tokenAuthType") TokenAuthType tokenAuthType);

  List<TokenAuth> findByUserIdIn(Set<String> userId);
}
