package com.sotatek.authservice.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {

  private static final Long MAX_AGE = 3600L;

  private static final int CORS_FILTER_ORDER = -102;

  private static final String CSRF_TOKEN_HEADER = "X-XSRF-TOKEN";

  @Value("${domain.client}")
  private String domain;

  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin(domain);
    config.setAllowedHeaders(
        Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, CSRF_TOKEN_HEADER,
            HttpHeaders.ACCEPT));
    config.setAllowedMethods(
        Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
            HttpMethod.OPTIONS.name(), HttpMethod.DELETE.name()));
    config.setMaxAge(MAX_AGE);
    source.registerCorsConfiguration("/**", config);
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
        new CorsFilter(source));
    filterRegistrationBean.setOrder(CORS_FILTER_ORDER);
    return filterRegistrationBean;
  }
}
