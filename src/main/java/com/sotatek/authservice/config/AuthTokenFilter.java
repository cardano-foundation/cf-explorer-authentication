package com.sotatek.authservice.config;

import com.sotatek.authservice.constant.AuthConstant;
import com.sotatek.authservice.exception.InvalidAccessTokenException;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.service.AuthenticationService;
import com.sotatek.authservice.service.UserService;
import java.io.IOException;
import java.util.stream.Stream;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private UserService userService;


  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
      throws ServletException, IOException {
    String jwt = jwtProvider.parseJwt(request);
    jwtProvider.validateJwtToken(jwt);
    if (authenticationService.isTokenBlacklisted(jwt)) {
      throw new InvalidAccessTokenException(jwt);
    }

    String username = jwtProvider.getUserNameFromJwtToken(jwt);
    if (username != null) {
      UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          username, null, userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(@NotNull HttpServletRequest request) throws ServletException {
    return Stream.of(AuthConstant.AUTH_WHITELIST, AuthConstant.USER_WHITELIST,
            AuthConstant.DOCUMENT_WHITELIST).flatMap(Stream::of)
        .anyMatch(x -> new AntPathMatcher().match(x, request.getServletPath()));
  }
}
