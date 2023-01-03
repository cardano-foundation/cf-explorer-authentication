package com.sotatek.authservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
  }
  //Todo confirm set csrf
  /**
   private static final Long MAX_AGE = 3600L;

   private static final int CORS_FILTER_ORDER = -102;

   private static final String CSRF_TOKEN_HEADER = "X-XSRF-TOKEN";

   @Value("${domain.client}") private String domain;

   @Bean public FilterRegistrationBean<CorsFilter> filterRegistrationBean() {
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
   FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>(
   new CorsFilter(source));
   filterRegistrationBean.setOrder(CORS_FILTER_ORDER);
   return filterRegistrationBean;
   }
   **/
}
