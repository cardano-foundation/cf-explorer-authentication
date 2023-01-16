package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.constant.CommonConstant;
import com.sotatek.authservice.mapper.UserHistoryMapper;
import com.sotatek.authservice.mapper.UserMapper;
import com.sotatek.authservice.model.entity.RoleEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.UserHistoryEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.authservice.model.enums.ERole;
import com.sotatek.authservice.model.enums.EStatus;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.request.EditUserRequest;
import com.sotatek.authservice.model.request.admin.SignUpAdminRequest;
import com.sotatek.authservice.model.request.auth.SignUpRequest;
import com.sotatek.authservice.model.response.ActivityLogResponse;
import com.sotatek.authservice.model.response.UserInfoResponse;
import com.sotatek.authservice.model.response.UserResponse;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.repository.BookMarkRepository;
import com.sotatek.authservice.repository.PrivateNoteRepository;
import com.sotatek.authservice.repository.RoleRepository;
import com.sotatek.authservice.repository.UserHistoryRepository;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.repository.WalletRepository;
import com.sotatek.authservice.service.UserHistoryService;
import com.sotatek.authservice.service.UserService;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final WalletRepository walletRepository;

  private final UserHistoryService userHistoryService;

  private final JwtProvider jwtProvider;

  private final BookMarkRepository bookMarkRepository;

  private final PrivateNoteRepository noteRepository;

  private final UserHistoryRepository userHistoryRepository;

  private final RoleRepository roleRepository;

  private static final UserMapper userMapper = UserMapper.INSTANCE;

  private static final UserHistoryMapper userHistoryMapper = UserHistoryMapper.INSTANCE;

  @Override
  public UserDetails loadUserByUsername(String param) throws UsernameNotFoundException {
    if (EmailValidator.getInstance().isValid(param)) {
      UserEntity user = userRepository.findByEmailAndStatus(param, EStatus.ACTIVE)
          .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
      return UserDetailsImpl.build(user);
    } else {
      WalletEntity wallet = walletRepository.findWalletByAddress(param)
          .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
      UserEntity user = wallet.getUser();
      return UserDetailsImpl.build(user, wallet);
    }
  }

  @Override
  public String findNonceByAddress(String address) {
    WalletEntity wallet = walletRepository.findWalletByAddress(address)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    return wallet.getNonce();
  }

  @Override
  public Boolean checkExistUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public UserResponse editAvatar(MultipartFile avatar, HttpServletRequest httpServletRequest) {
    log.info("edit user is running...");
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    if (Objects.nonNull(avatar)) {
      StringBuilder base64Image = new StringBuilder(CommonConstant.BASE64_PREFIX);
      try {
        base64Image.append(Base64.getEncoder().encodeToString(avatar.getBytes()));
        user.setAvatar(base64Image.toString());
      } catch (IOException e) {
        log.error("error: convert image file to byte[]");
      }
    }
    UserEntity userEdit = userRepository.save(user);
    userHistoryService.saveUserHistory(EUserAction.UPDATED, null, Instant.now(), null, userEdit);
    return userMapper.entityToResponse(userEdit);
  }

  @Override
  public UserInfoResponse infoUser(HttpServletRequest httpServletRequest) {
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    String address = jwtProvider.getIdFromJwtToken(token);
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    Integer sizeBookMark = bookMarkRepository.getCountBookMarkByUser(user.getId());
    Integer sizeNote = noteRepository.getCountNoteByUser(user.getId());
    UserHistoryEntity userHistory = userHistoryRepository.findFirstByUserAndUserActionOrderByActionTimeDesc(
        user, EUserAction.LOGIN);
    return UserInfoResponse.builder().username(username).email(user.getEmail())
        .avatar(user.getAvatar()).sizeBookmark(sizeBookMark).sizeNote(sizeNote).wallet(address)
        .lastLogin(userHistory.getActionTime()).build();
  }

  @Override
  public List<ActivityLogResponse> getLog(HttpServletRequest httpServletRequest) {
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    List<UserHistoryEntity> historyList = userHistoryRepository.findTop10ByUserOrderByActionTimeDesc(
        user);
    List<ActivityLogResponse> logList = userHistoryMapper.listEntityToResponse(historyList);
    logList.forEach(log -> log.setStrAction(log.getUserAction().getAction()));
    return logList;
  }

  @Override
  public UserEntity saveUser(SignUpRequest signUpRequest) {
    UserEntity user = userMapper.requestToEntity(signUpRequest);
    user.setRoles(addRoleForUser(ERole.ROLE_USER));
    return userRepository.save(user);
  }

  @Override
  public UserEntity findUserByWalletAddress(String address) {
    return userRepository.findUserByWalletAddress(address)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
  }

  @Override
  public UserEntity findByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
  }

  @Override
  public Boolean checkExistEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public UserEntity saveUserAdmin(SignUpAdminRequest signUpAdmin) {
    UserEntity user = userMapper.requestAdminToEntity(signUpAdmin);
    user.setRoles(addRoleForUser(signUpAdmin.getRole()));
    user.setStatus(EStatus.PENDING);
    return userRepository.save(user);
  }

  @Override
  public UserEntity activeUserAdmin(String username) {
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    user.setStatus(EStatus.ACTIVE);
    return userRepository.save(user);
  }

  @Override
  public UserEntity findByEmailAndStatus(String email, EStatus status) {
    return userRepository.findByEmailAndStatus(email, status)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
  }

  @Override
  public UserEntity findByUsernameAndStatus(String username, EStatus status) {
    return userRepository.findByUsernameAndStatus(username, status).orElse(null);
  }

  @Override
  public UserResponse editUser(EditUserRequest editUserRequest,
      HttpServletRequest httpServletRequest) {
    log.info("edit user is running...");
    String usernameReq = editUserRequest.getUsername();
    String emailReq = editUserRequest.getEmail();
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    if (Objects.nonNull(emailReq) && Boolean.FALSE.equals(checkExistEmail(emailReq))) {
      user.setEmail(emailReq);
    }
    if (Objects.nonNull(usernameReq) && Boolean.FALSE.equals(checkExistUsername(usernameReq))) {
      user.setUsername(usernameReq);
    }
    UserEntity userEdit = userRepository.save(user);
    userHistoryService.saveUserHistory(EUserAction.UPDATED, null, Instant.now(), null, userEdit);
    return userMapper.entityToResponse(userEdit);
  }

  /*
   * @author: phuc.nguyen5
   * @since: 22/12/2022
   * description: get role for user
   * @update:
   */
  private Set<RoleEntity> addRoleForUser(ERole eRole) {
    Set<RoleEntity> roles = new HashSet<>();
    switch (eRole) {
      case ROLE_ADMIN:
        RoleEntity rAdmin = roleRepository.findByName(ERole.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
        roles.add(rAdmin);
        break;
      case ROLE_USER:
        RoleEntity rUser = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
        roles.add(rUser);
        break;
      case ROLE_MODERATOR:
        RoleEntity rMode = roleRepository.findByName(ERole.ROLE_MODERATOR)
            .orElseThrow(() -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
        roles.add(rMode);
        break;
      default:
    }
    return roles;
  }
}
