package org.cardanofoundation.authentication.config;

import lombok.RequiredArgsConstructor;
import org.cardanofoundation.authentication.constant.AuthConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final AuthEntryPointJwt unauthorizedHandler;

  private final AuthTokenFilter authTokenFilter;

  @Bean
  public ExceptionHandlerFilter exceptionHandlerFilter() {
    return new ExceptionHandlerFilter();
  }

  @Bean
  public AuthenticationManager authenticationManagerBean(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable().cors().and().exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests()
        .requestMatchers(AuthConstant.CLIENT_WHITELIST).permitAll()
        .requestMatchers(AuthConstant.AUTH_WHITELIST).permitAll()
        .requestMatchers(AuthConstant.USER_WHITELIST).permitAll()
        .requestMatchers(AuthConstant.DOCUMENT_WHITELIST).permitAll().anyRequest().authenticated();
    http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(exceptionHandlerFilter(), AuthTokenFilter.class);
    return http.build();
  }
}
