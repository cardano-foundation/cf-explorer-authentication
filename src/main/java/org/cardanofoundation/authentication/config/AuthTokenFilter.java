package org.cardanofoundation.authentication.config;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

import org.cardanofoundation.authentication.constant.AuthConstant;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.KeycloakProvider;
import org.cardanofoundation.authentication.provider.RedisProvider;
import org.cardanofoundation.explorer.common.exception.InvalidAccessTokenException;

@Log4j2
@RequiredArgsConstructor
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;

  private final RedisProvider redisProvider;

  private final KeycloakProvider keycloakProvider;

  @Override
  protected void doFilterInternal(
      @NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response,
      @NotNull FilterChain filterChain)
      throws ServletException, IOException {
    String token = jwtProvider.parseJwt(request);
    jwtProvider.validateJwtToken(token);
    if (redisProvider.isTokenBlacklisted(token)) {
      throw new InvalidAccessTokenException();
    }

    String accountId = jwtProvider.getAccountIdFromJwtToken(token);
    UsersResource usersResource = keycloakProvider.getResource();
    UserRepresentation user = usersResource.get(accountId).toRepresentation();
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(user.getUsername(), null, fillAuthorities(token));
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(@NotNull HttpServletRequest request) throws ServletException {
    return Stream.of(
            AuthConstant.AUTH_WHITELIST,
            AuthConstant.USER_WHITELIST,
            AuthConstant.DOCUMENT_WHITELIST,
            AuthConstant.CLIENT_WHITELIST)
        .flatMap(Stream::of)
        .anyMatch(x -> new AntPathMatcher().match(x, request.getServletPath()));
  }

  private List<SimpleGrantedAuthority> fillAuthorities(String token) {
    List<String> roles = jwtProvider.getRolesFromJwtToken(token);
    if (roles.isEmpty()) {
      return Collections.emptyList();
    }
    return roles.stream().map(SimpleGrantedAuthority::new).toList();
  }
}
