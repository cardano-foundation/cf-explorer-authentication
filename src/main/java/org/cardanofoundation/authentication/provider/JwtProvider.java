package org.cardanofoundation.authentication.provider;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

import org.cardanofoundation.authentication.config.RsaConfig;
import org.cardanofoundation.authentication.config.properties.MailProperties;
import org.cardanofoundation.explorer.common.exception.BusinessException;
import org.cardanofoundation.explorer.common.exception.CommonErrorCode;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtProvider {

  private final RsaConfig rsaConfig;

  private final MailProperties mail;

  public String generateCodeForVerify(String email) {
    return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + mail.getExpirationMs()))
        .signWith(rsaConfig.getPrivateKeyMail(), SignatureAlgorithm.RS256)
        .compact();
  }

  public String getAccountIdFromJwtToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(rsaConfig.getPublicKeyAuth())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  @SuppressWarnings("unchecked")
  public List<String> getRolesFromJwtToken(String token) {
    Claims claims =
        Jwts.parserBuilder()
            .setSigningKey(rsaConfig.getPublicKeyAuth())
            .build()
            .parseClaimsJws(token)
            .getBody();
    Map<String, List<String>> realmAccessMap =
        (Map<String, List<String>>) claims.get("realm_access");
    if (Objects.nonNull(realmAccessMap)) {
      List<String> allRoles = realmAccessMap.get("roles");
      return allRoles.stream().filter(role -> role.startsWith("ROLE_")).toList();
    }
    return Collections.emptyList();
  }

  public String getAccountIdFromJwtToken(HttpServletRequest httpServletRequest) {
    String token = parseJwt(httpServletRequest);
    return Jwts.parserBuilder()
        .setSigningKey(rsaConfig.getPublicKeyAuth())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public String parseJwt(HttpServletRequest request) {
    final String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }

  public void validateJwtToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(rsaConfig.getPublicKeyAuth())
          .build()
          .parseClaimsJws(token);
    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
      throw new BusinessException(CommonErrorCode.TOKEN_INVALID_SIGNATURE);
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
      throw new BusinessException(CommonErrorCode.INVALID_TOKEN);
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
      throw new BusinessException(CommonErrorCode.TOKEN_EXPIRED);
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
      throw new BusinessException(CommonErrorCode.TOKEN_UNSUPPORTED);
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
      throw new BusinessException(CommonErrorCode.TOKEN_IS_EMPTY);
    }
  }

  public Boolean validateVerifyCode(String code) {
    try {
      Jwts.parserBuilder().setSigningKey(rsaConfig.getPublicKeyMail()).build().parseClaimsJws(code);
    } catch (RuntimeException e) {
      log.error("Invalid verify code: {}", e.getMessage());
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }

  public String getAccountIdFromVerifyCode(String code) {
    return Jwts.parserBuilder()
        .setSigningKey(rsaConfig.getPublicKeyMail())
        .build()
        .parseClaimsJws(code)
        .getBody()
        .getSubject();
  }
}
