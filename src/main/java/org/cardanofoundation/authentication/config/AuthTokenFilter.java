package org.cardanofoundation.authentication.config;

import org.cardanofoundation.explorer.common.exceptions.InvalidAccessTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.constant.AuthConstant;
import org.cardanofoundation.authentication.model.entity.security.UserDetailsImpl;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.provider.RedisProvider;
import org.cardanofoundation.authentication.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2
@RequiredArgsConstructor
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;

  private final RedisProvider redisProvider;

  private final UserService userService;

  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
      throws ServletException, IOException {
    String token = jwtProvider.parseJwt(request);
    jwtProvider.validateJwtToken(token);
    if (redisProvider.isTokenBlacklisted(token)) {
      throw new InvalidAccessTokenException();
    }

    String username = jwtProvider.getUserNameFromJwtToken(token);
    UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetails.getUsername(), null, userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(@NotNull HttpServletRequest request) throws ServletException {
    return Stream.of(AuthConstant.AUTH_WHITELIST, AuthConstant.USER_WHITELIST,
            AuthConstant.DOCUMENT_WHITELIST, AuthConstant.CLIENT_WHITELIST).flatMap(Stream::of)
        .anyMatch(x -> new AntPathMatcher().match(x, request.getServletPath()));
  }
}
