package org.cardanofoundation.authentication.provider;

import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.config.RsaConfig;
import org.cardanofoundation.authentication.config.properties.MailProperties;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.entity.RoleEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.entity.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

  public String generateJwtToken(Authentication authentication, String accountId) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    return Jwts.builder().setSubject(accountId)
        .claim(CommonConstant.AUTHORITIES_KEY,
            userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toList()).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + expirationMs))
        .signWith(rsaConfig.getPrivateKeyAuth(), SignatureAlgorithm.RS256).compact();
  }

  public String generateCodeForVerify(String email) {
    return Jwts.builder().setSubject(email).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + mail.getExpirationMs()))
        .signWith(rsaConfig.getPrivateKeyMail(), SignatureAlgorithm.RS256).compact();
  }

  public String getAccountIdFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(rsaConfig.getPublicKeyAuth()).build()
        .parseClaimsJws(token).getBody().getSubject();
  }

  public String getAccountIdFromJwtToken(HttpServletRequest httpServletRequest) {
    String token = parseJwt(httpServletRequest);
    return Jwts.parserBuilder().setSigningKey(rsaConfig.getPublicKeyAuth()).build()
        .parseClaimsJws(token).getBody().getSubject();
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

  public String getAccountIdFromVerifyCode(String code) {
    return Jwts.parserBuilder().setSigningKey(rsaConfig.getPublicKeyMail()).build()
        .parseClaimsJws(code).getBody().getSubject();
  }

  public String generateJwtToken(UserEntity user, String accountId) {
    return Jwts.builder().setSubject(accountId)
        .claim(CommonConstant.AUTHORITIES_KEY,
            user.getRoles().stream().map(RoleEntity::getName).toList())
        .setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + expirationMs))
        .signWith(rsaConfig.getPrivateKeyAuth(), SignatureAlgorithm.RS256).compact();
  }
}
