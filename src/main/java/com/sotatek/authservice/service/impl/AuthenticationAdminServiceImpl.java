package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.constant.CommonConstant;
import com.sotatek.authservice.model.entity.RefreshTokenEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.authservice.model.enums.EStatus;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.request.admin.RemoveUserRequest;
import com.sotatek.authservice.model.request.admin.SignInAdminRequest;
import com.sotatek.authservice.model.request.admin.SignUpAdminRequest;
import com.sotatek.authservice.model.request.auth.SignOutRequest;
import com.sotatek.authservice.model.response.MessageResponse;
import com.sotatek.authservice.model.response.auth.RefreshTokenResponse;
import com.sotatek.authservice.model.response.auth.SignInResponse;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.provider.MailProvider;
import com.sotatek.authservice.provider.RedisProvider;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.service.AuthenticationAdminService;
import com.sotatek.authservice.service.RefreshTokenService;
import com.sotatek.authservice.service.UserService;
import com.sotatek.authservice.thread.MailHandler;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.InvalidAccessTokenException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationAdminServiceImpl implements AuthenticationAdminService {

  private final UserRepository userRepository;

  private final UserService userService;

  private final MailProvider mailProvider;

  private final PasswordEncoder encoder;

  private final JwtProvider jwtProvider;

  private final ThreadPoolExecutor sendMailExecutor;

  private final AuthenticationManager authenticationManager;

  private final RefreshTokenService refreshTokenService;

  private final RedisProvider redisProvider;

  @Override
  public MessageResponse signUp(SignUpAdminRequest signUpAdmin) {
    String username = signUpAdmin.getUsername();
    if (Boolean.TRUE.equals(userService.checkExistUsername(username))) {
      throw new BusinessException(CommonErrorCode.USERNAME_IS_ALREADY_EXIST);
    }
    String email = signUpAdmin.getEmail();
    if (Boolean.TRUE.equals(userService.checkExistEmail(email))) {
      throw new BusinessException(CommonErrorCode.EMAIL_IS_ALREADY_EXIST);
    }
    signUpAdmin.setPassword(encoder.encode(signUpAdmin.getPassword()));
    UserEntity user = userService.saveUserAdmin(signUpAdmin);
    String verifyCode = jwtProvider.generateCodeForVerify(user.getUsername());
    sendMailExecutor.execute(new MailHandler(mailProvider, user, EUserAction.CREATED, verifyCode));
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public SignInResponse signIn(SignInAdminRequest signInAdmin) {
    log.info("login with admin account is running...");
    String email = signInAdmin.getEmail();
    String password = signInAdmin.getPassword();
    Authentication authentication;
    try {
      authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(email, password));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (AuthenticationException e) {
      log.error("Exception authentication: " + e.getMessage());
      throw new BusinessException(CommonErrorCode.USERNAME_OR_PASSWORD_INVALID);
    }
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserEntity user = userService.findByEmailAndStatus(email, EStatus.ACTIVE);
    String accessToken = jwtProvider.generateJwtToken(authentication, user.getUsername());
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    RefreshTokenEntity refreshToken = refreshTokenService.addRefreshToken(user);
    return SignInResponse.builder().token(accessToken).username(user.getUsername())
        .email(userDetails.getEmail()).tokenType(CommonConstant.TOKEN_TYPE)
        .refreshToken(refreshToken.getToken()).build();
  }

  @Override
  public RefreshTokenResponse refreshToken(String refreshJwt,
      HttpServletRequest httpServletRequest) {
    final String accessToken = jwtProvider.parseJwt(httpServletRequest);
    return refreshTokenService.findByRefToken(refreshJwt).map(refreshTokenService::verifyExpiration)
        .map(refToken -> {
          UserEntity user = refToken.getUser();
          redisProvider.blacklistJwt(accessToken, user.getUsername());
          return jwtProvider.generateJwtTokenFromUser(user);
        }).map(newAccessToken -> RefreshTokenResponse.builder().accessToken(newAccessToken)
            .refreshToken(refreshJwt).tokenType(CommonConstant.TOKEN_TYPE).build())
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
  }

  @Override
  public MessageResponse signOut(SignOutRequest signOutRequest,
      HttpServletRequest httpServletRequest) {
    String username = signOutRequest.getUsername();
    String refreshJwt = signOutRequest.getRefreshJwt();
    String accessToken = jwtProvider.parseJwt(httpServletRequest);
    refreshTokenService.revokeRefreshToken(refreshJwt);
    redisProvider.blacklistJwt(accessToken, username);
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public MessageResponse remove(RemoveUserRequest removeUserRequest,
      HttpServletRequest httpServletRequest) {
    final String accessToken = jwtProvider.parseJwt(httpServletRequest);
    if (redisProvider.isTokenBlacklisted(accessToken)) {
      throw new InvalidAccessTokenException();
    }
    String username = jwtProvider.getUserNameFromJwtToken(accessToken);
    UserEntity user = userService.findByUsernameAndStatus(username, EStatus.ACTIVE);
    if (Objects.isNull(user) || !encoder.matches(removeUserRequest.getPassword(),
        user.getPassword())) {
      return new MessageResponse(CommonConstant.CODE_FAILURE, CommonConstant.RESPONSE_FAILURE);
    }
    refreshTokenService.revokeRefreshTokenByUsername(username);
    redisProvider.blacklistJwt(accessToken, username);
    user.setDeleted(Boolean.TRUE);
    userRepository.save(user);
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }
}
