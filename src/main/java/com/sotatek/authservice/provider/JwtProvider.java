package com.sotatek.authservice.provider;

import com.sotatek.authservice.config.RsaConfig;
import com.sotatek.authservice.config.properties.MailProperties;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtProvider {

  @Value("${jwt.expirationMs}")
  private Long expirationMs;

  private final RsaConfig rsaConfig;

  private final MailProperties mail;


  public String generateJwtToken(Authentication authentication, String username) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    return Jwts.builder().setSubject(username).setId(String.valueOf(userPrincipal.getId()))
        .setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + expirationMs))
        .signWith(rsaConfig.getPrivateKeyAuth(), SignatureAlgorithm.RS256).compact();
  }

  public String generateJwtTokenFromUsername(String username, Long walletId) {
    return Jwts.builder().setSubject(username).setId(String.valueOf(walletId))
        .setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + expirationMs))
        .signWith(rsaConfig.getPrivateKeyAuth(), SignatureAlgorithm.RS256).compact();
  }

  public String generateJwtForVerifyAdmin(String username) {
    return Jwts.builder().setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + mail.getExpirationMs()))
        .signWith(rsaConfig.getPrivateKeyMail(), SignatureAlgorithm.RS256).compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(rsaConfig.getPublicKeyAuth()).build()
        .parseClaimsJws(token)
        .getBody().getSubject();
  }

  public String getWalletIdFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(rsaConfig.getPublicKeyAuth()).build()
        .parseClaimsJws(token)
        .getBody().getId();
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
      Jwts.parserBuilder().setSigningKey(rsaConfig.getPublicKeyAuth()).build()
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
      throw new BusinessException(CommonErrorCode.TOKEN_IS_NOT_EMPTY);
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

  public String getUserNameFromVerifyCode(String code) {
    return Jwts.parserBuilder().setSigningKey(rsaConfig.getPublicKeyMail()).build()
        .parseClaimsJws(code)
        .getBody().getSubject();
  }
}
