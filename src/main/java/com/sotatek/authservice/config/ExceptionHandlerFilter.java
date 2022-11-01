package com.sotatek.authservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sotatek.cardanocommonapi.exceptions.AccessTokenExpireException;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.ErrorResponse;
import com.sotatek.cardanocommonapi.exceptions.InvalidAccessTokenException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2
public class ExceptionHandlerFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest httpServletRequest,
      @NotNull HttpServletResponse httpServletResponse, @NotNull FilterChain filterChain)
      throws ServletException, IOException {
    httpServletResponse.setContentType("application/json");
    httpServletResponse.setCharacterEncoding("UTF-8");
    try {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
    } catch (InvalidAccessTokenException | AccessTokenExpireException e) {
      log.error(e.getMessage());
      ErrorResponse errorResponse = ErrorResponse.builder()
          .errorCode(e.getErrorCode().getServiceErrorCode())
          .errorMessage(e.getErrorCode().getDesc()).build();
      httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpServletResponse.getWriter().write(convertObjectToJson(errorResponse));
    } catch (BusinessException e) {
      log.error(e.getMessage());
      ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode())
          .errorMessage(e.getErrorMsg()).build();
      httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpServletResponse.getWriter().write(convertObjectToJson(errorResponse));
    } catch (Exception e) {
      log.error(e.getMessage());
      ErrorResponse errorResponse = ErrorResponse.builder()
          .errorCode(CommonErrorCode.UNKNOWN_ERROR.getServiceErrorCode())
          .errorMessage(CommonErrorCode.UNKNOWN_ERROR.getDesc()).build();
      httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      httpServletResponse.getWriter().write(convertObjectToJson(errorResponse));
    }
  }

  public String convertObjectToJson(Object object) throws JsonProcessingException {
    if (object == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }
}
