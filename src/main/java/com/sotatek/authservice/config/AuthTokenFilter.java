package com.sotatek.authservice.config;

import com.sotatek.authservice.constant.AuthConstant;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.provider.RedisProvider;
import com.sotatek.authservice.repository.WalletRepository;
import com.sotatek.authservice.service.UserService;
import com.sotatek.cardanocommonapi.exceptions.InvalidAccessTokenException;
import java.io.IOException;
import java.util.stream.Stream;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
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

  private final WalletRepository walletRepository;


  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
      throws ServletException, IOException {
    String token = jwtProvider.parseJwt(request);
    jwtProvider.validateJwtToken(token);
    if (redisProvider.isTokenBlacklisted(token)) {
      throw new InvalidAccessTokenException();
    }

    String param = jwtProvider.getIdFromJwtToken(token);
    UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(param);
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetails.getUsername(), null, userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(@NotNull HttpServletRequest request) throws ServletException {
    return Stream.of(AuthConstant.AUTH_WHITELIST, AuthConstant.USER_WHITELIST,
            AuthConstant.CSRF_TOKEN_PATH, AuthConstant.DOCUMENT_WHITELIST).flatMap(Stream::of)
        .anyMatch(x -> new AntPathMatcher().match(x, request.getServletPath()));
  }
}
