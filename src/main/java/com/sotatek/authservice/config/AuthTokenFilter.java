package com.sotatek.authservice.config;

import com.sotatek.authservice.constant.AuthConstant;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.service.AuthenticationService;
import com.sotatek.authservice.service.UserService;
import com.sotatek.authservice.service.WalletService;
import com.sotatek.cardanocommonapi.exceptions.InvalidAccessTokenException;
import com.sotatek.cardanocommonapi.utils.StringUtils;
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

  @Autowired
  private WalletService walletService;


  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
      throws ServletException, IOException {
    String token = jwtProvider.parseJwt(request);
    jwtProvider.validateJwtToken(token);
    if (authenticationService.isTokenBlacklisted(token)) {
      throw new InvalidAccessTokenException();
    }

    String walletId = jwtProvider.getWalletIdFromJwtToken(token);
    if (Boolean.TRUE.equals(StringUtils.isNotBlank(walletId))) {
      String stakeAddress = walletService.getStakeAddressByWalletId(Long.valueOf(walletId));
      UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(stakeAddress);
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          stakeAddress, null, userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(@NotNull HttpServletRequest request) throws ServletException {
    return Stream.of(AuthConstant.AUTH_WHITELIST, AuthConstant.USER_WHITELIST,
            AuthConstant.CSRF_TOKEN_PATH, AuthConstant.DOCUMENT_WHITELIST).flatMap(Stream::of)
        .anyMatch(x -> new AntPathMatcher().match(x, request.getServletPath()));
  }
}
