package com.sotatek.authservice.aop;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.request.auth.SignInRequest;
import com.sotatek.authservice.model.request.auth.SignOutRequest;
import com.sotatek.authservice.model.request.auth.SignUpRequest;
import com.sotatek.authservice.model.request.auth.TransfersWalletRequest;
import com.sotatek.authservice.service.UserHistoryService;
import com.sotatek.authservice.service.UserService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
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

  @AfterReturning("execution(* com.sotatek.authservice.service.impl.AuthenticationServiceImpl.signIn(*)) && args(signInRequest)")
  public void signInLog(SignInRequest signInRequest) {
    String address = signInRequest.getAddress();
    UserEntity user = userService.findUserByWalletAddress(address);
    userHistoryService.saveUserHistory(EUserAction.LOGIN, signInRequest.getIpAddress(),
        Instant.now(), address, user);
  }

  @AfterReturning("execution(* com.sotatek.authservice.service.impl.AuthenticationServiceImpl.signUp(*)) && args(signUpRequest)")
  public void signUpLog(SignUpRequest signUpRequest) {
    String address = signUpRequest.getWallet().getAddress();
    UserEntity user = userService.findUserByWalletAddress(address);
    userHistoryService.saveUserHistory(EUserAction.CREATED, null, Instant.now(), address, user);
  }

  @AfterReturning("execution(* com.sotatek.authservice.service.impl.AuthenticationServiceImpl.transfersWallet(com.sotatek.authservice.model.request.auth.TransfersWalletRequest,..)) && args(transfersWalletRequest,..)")
  public void transfersWalletLog(TransfersWalletRequest transfersWalletRequest) {
    String username = transfersWalletRequest.getUsername();
    String address = transfersWalletRequest.getWallet().getAddress();
    UserEntity user = userService.findByUsername(username);
    userHistoryService.saveUserHistory(EUserAction.TRANSFERS_WALLET, null, Instant.now(),
        address, user);
  }

  @AfterReturning("execution(* com.sotatek.authservice.service.impl.AuthenticationServiceImpl.signOut(com.sotatek.authservice.model.request.auth.SignOutRequest,..)) && args(signOutRequest,..)")
  public void signOutLog(SignOutRequest signOutRequest) {
    String username = signOutRequest.getUsername();
    UserEntity user = userService.findByUsername(username);
    userHistoryService.saveUserHistory(EUserAction.LOGOUT, null, Instant.now(), null, user);
  }
}
