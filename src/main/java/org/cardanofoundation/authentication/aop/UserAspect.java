package org.cardanofoundation.authentication.aop;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.model.request.auth.SignInRequest;
import org.cardanofoundation.authentication.model.request.auth.SignOutRequest;
import org.cardanofoundation.authentication.model.request.auth.SignUpRequest;
import org.cardanofoundation.authentication.service.UserHistoryService;
import org.cardanofoundation.authentication.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
@Configuration
@EnableAspectJAutoProxy
@RequiredArgsConstructor
@Log4j2
public class UserAspect {

  private final UserHistoryService userHistoryService;

  private final UserService userService;

  @AfterReturning("execution(* org.cardanofoundation.authentication.service.impl.AuthenticationServiceImpl.signIn(*)) && args(signInRequest)")
  public void signInLog(SignInRequest signInRequest) {
    String username = "";
    Integer type = signInRequest.getType();
    if (type == 0) {
      username = signInRequest.getUsername();
    } else {
      username = signInRequest.getAddress();
    }
    UserEntity user = userService.findByUsername(username);
    userHistoryService.saveUserHistory(EUserAction.LOGIN, Instant.now(), user);
  }

  @AfterReturning("execution(* org.cardanofoundation.authentication.service.impl.AuthenticationServiceImpl.signUp(*)) && args(signUpRequest)")
  public void signUpLog(SignUpRequest signUpRequest) {
    UserEntity user = userService.findByUsername(signUpRequest.getUsername());
    userHistoryService.saveUserHistory(EUserAction.CREATED, Instant.now(), user);
  }

  @AfterReturning("execution(* org.cardanofoundation.authentication.service.impl.AuthenticationServiceImpl.signOut(org.cardanofoundation.authentication.model.request.auth.SignOutRequest,..)) && args(signOutRequest,..)")
  public void signOutLog(SignOutRequest signOutRequest) {
    String username = signOutRequest.getUsername();
    UserEntity user = userService.findByUsername(username);
    userHistoryService.saveUserHistory(EUserAction.LOGOUT, Instant.now(), user);
  }
}
