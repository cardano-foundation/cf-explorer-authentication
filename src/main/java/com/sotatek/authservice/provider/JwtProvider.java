package com.sotatek.authservice.provider;

import com.sotatek.authservice.config.RsaConfig;
import com.sotatek.authservice.model.entity.UserEntity;
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
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Log4j2
public class JwtProvider {

  @Value("${jwt.expirationMs}")
  private Long expirationMs;

  @Autowired
  private RsaConfig rsaConfig;


  public String generateJwtToken(Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    return Jwts.builder().setId(String.valueOf(userPrincipal.getId()))
        .setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + expirationMs))
        .signWith(rsaConfig.getRsaKey(), SignatureAlgorithm.RS256).compact();
  }

  public String generateTokenFromUsername(UserEntity user) {
    return Jwts.builder().setSubject(user.getUsername()).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + expirationMs))
        .signWith(rsaConfig.getRsaKey(), SignatureAlgorithm.RS256).compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(rsaConfig.getRsaKey()).build().parseClaimsJws(token)
        .getBody().getSubject();
  }

  public String parseJwt(HttpServletRequest request) {
    final String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }

  public void validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(rsaConfig.getRsaKey()).build().parseClaimsJws(authToken);
    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
      throw BusinessException.builder()
          .errorCode(CommonErrorCode.TOKEN_INVALID_SIGNATURE.getServiceErrorCode())
          .errorMsg(CommonErrorCode.TOKEN_INVALID_SIGNATURE.getDesc()).build();
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
      throw BusinessException.builder()
          .errorCode(CommonErrorCode.INVALID_TOKEN.getServiceErrorCode())
          .errorMsg(CommonErrorCode.INVALID_TOKEN.getDesc()).build();
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
      throw BusinessException.builder()
          .errorCode(CommonErrorCode.TOKEN_EXPIRED.getServiceErrorCode())
          .errorMsg(CommonErrorCode.TOKEN_EXPIRED.getDesc()).build();
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
      throw BusinessException.builder()
          .errorCode(CommonErrorCode.TOKEN_UNSUPPORTED.getServiceErrorCode())
          .errorMsg(CommonErrorCode.TOKEN_UNSUPPORTED.getDesc()).build();
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
      throw BusinessException.builder()
          .errorCode(CommonErrorCode.TOKEN_IS_NOT_EMPTY.getServiceErrorCode())
          .errorMsg(CommonErrorCode.TOKEN_IS_NOT_EMPTY.getDesc()).build();
    }
  }
}
