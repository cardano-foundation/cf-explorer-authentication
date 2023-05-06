package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.constant.CommonConstant;
import com.sotatek.authservice.mapper.UserMapper;
import com.sotatek.authservice.model.entity.RoleEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.UserHistoryEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.authservice.model.enums.ENetworkType;
import com.sotatek.authservice.model.enums.ERole;
import com.sotatek.authservice.model.enums.EStatus;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.request.EditUserRequest;
import com.sotatek.authservice.model.request.admin.SignUpAdminRequest;
import com.sotatek.authservice.model.request.auth.SignUpRequest;
import com.sotatek.authservice.model.response.UserInfoResponse;
import com.sotatek.authservice.model.response.UserResponse;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.repository.BookMarkRepository;
import com.sotatek.authservice.repository.PrivateNoteRepository;
import com.sotatek.authservice.repository.RoleRepository;
import com.sotatek.authservice.repository.UserHistoryRepository;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.repository.WalletRepository;
import com.sotatek.authservice.service.UserService;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

  private final JwtProvider jwtProvider;

  private final BookMarkRepository bookMarkRepository;

  private final PrivateNoteRepository noteRepository;

  private final UserHistoryRepository userHistoryRepository;

  private final RoleRepository roleRepository;

  private static final UserMapper userMapper = UserMapper.INSTANCE;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByUsernameAndStatus(username, EStatus.ACTIVE)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    String password = user.getPassword();
    WalletEntity wallet = walletRepository.findWalletByAddress(username).orElse(null);
    if (Objects.nonNull(wallet)) {
      password = wallet.getNonceEncode();
    }
    return UserDetailsImpl.build(user, password);
  }

  @Override
  public Boolean checkExistUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public UserResponse editAvatar(MultipartFile avatar, HttpServletRequest httpServletRequest) {
    log.info("edit user image is running...");
    String username = jwtProvider.getUserNameFromJwtToken(httpServletRequest);
    UserEntity user = findByUsername(username);
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
    return userMapper.entityToResponse(userEdit);
  }

  @Override
  public UserInfoResponse infoUser(HttpServletRequest httpServletRequest, ENetworkType network) {
    String username = jwtProvider.getUserNameFromJwtToken(httpServletRequest);
    UserEntity user = findByUsername(username);
    Integer sizeBookMark = bookMarkRepository.getCountBookMarkByUser(user.getId(), network);
    Integer sizeNote = noteRepository.getCountNoteByUser(user.getId(), network);
    UserHistoryEntity userHistory = userHistoryRepository.findFirstByUserAndUserActionOrderByActionTimeDesc(
        user, EUserAction.LOGIN);
    if (Objects.isNull(userHistory)) {
      userHistory = userHistoryRepository.findFirstByUserAndUserActionOrderByActionTimeDesc(user,
          EUserAction.CREATED);
    }
    return UserInfoResponse.builder().username(username).email(user.getEmail())
        .avatar(user.getAvatar()).sizeBookmark(sizeBookMark).sizeNote(sizeNote)
        .lastLogin(userHistory.getActionTime()).build();
  }

  @Override
  public UserEntity saveUser(SignUpRequest signUpRequest) {
    UserEntity user = userMapper.requestToEntity(signUpRequest);
    user.setStatus(EStatus.PENDING);
    user.setRoles(addRoleForUser(ERole.ROLE_USER));
    return userRepository.save(user);
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
  public void activeUser(String username) {
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    user.setStatus(EStatus.ACTIVE);
    userRepository.save(user);
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
    String emailReq = editUserRequest.getEmail();
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    UserEntity user = findByUsername(username);
    if (Objects.nonNull(emailReq)) {
      if (Boolean.TRUE.equals(checkExistEmail(emailReq))) {
        throw new BusinessException(CommonErrorCode.EMAIL_IS_ALREADY_EXIST);
      }
      user.setEmail(emailReq);
    }
    UserEntity userEdit = userRepository.save(user);
    return userMapper.entityToResponse(userEdit);
  }

  @Override
  public UserEntity saveUser(String address) {
    UserEntity user = UserEntity.builder()
        .username(address)
        .status(EStatus.ACTIVE)
        .roles(addRoleForUser(ERole.ROLE_USER))
        .build();
    return userRepository.save(user);
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
