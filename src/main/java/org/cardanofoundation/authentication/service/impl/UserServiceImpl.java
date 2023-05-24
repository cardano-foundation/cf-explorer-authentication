package org.cardanofoundation.authentication.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.mapper.UserMapper;
import org.cardanofoundation.authentication.model.entity.RoleEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.entity.UserHistoryEntity;
import org.cardanofoundation.authentication.model.entity.WalletEntity;
import org.cardanofoundation.authentication.model.entity.security.UserDetailsImpl;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.enums.ERole;
import org.cardanofoundation.authentication.model.enums.EStatus;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.model.request.EditUserRequest;
import org.cardanofoundation.authentication.model.request.auth.SignUpRequest;
import org.cardanofoundation.authentication.model.response.UserInfoResponse;
import org.cardanofoundation.authentication.model.response.UserResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.repository.BookMarkRepository;
import org.cardanofoundation.authentication.repository.PrivateNoteRepository;
import org.cardanofoundation.authentication.repository.RoleRepository;
import org.cardanofoundation.authentication.repository.UserHistoryRepository;
import org.cardanofoundation.authentication.repository.UserRepository;
import org.cardanofoundation.authentication.repository.WalletRepository;
import org.cardanofoundation.authentication.service.UserService;
import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
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
  public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByEmailAndStatus(accountId, EStatus.ACTIVE).orElse(null);
    String password = "";
    if (Objects.isNull(user)) {
      WalletEntity wallet = walletRepository.findWalletByAddress(accountId)
          .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
      password = wallet.getNonceEncode();
      user = wallet.getUser();
    } else {
      password = user.getPassword();
    }
    return UserDetailsImpl.build(user, accountId, password);
  }

  @Override
  public UserResponse editAvatar(MultipartFile avatar, HttpServletRequest httpServletRequest) {
    log.info("edit user image is running...");
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserEntity user = findByAccountId(accountId);
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
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserEntity user = findByAccountId(accountId);
    Long userId = user.getId();
    Integer sizeBookMark = bookMarkRepository.getCountBookMarkByUser(userId, network);
    Integer sizeNote = noteRepository.getCountNoteByUser(userId, network);
    UserHistoryEntity userHistory = userHistoryRepository.findFirstByUserAndUserActionOrderByActionTimeDesc(
        user, EUserAction.LOGIN);
    if (Objects.isNull(userHistory)) {
      userHistory = userHistoryRepository.findFirstByUserAndUserActionOrderByActionTimeDesc(user,
          EUserAction.CREATED);
    }
    String address = walletRepository.findAddressByUserId(userId);
    if (Objects.isNull(address)) {
      address = user.getStakeKey();
    }
    return UserInfoResponse.builder().address(address).email(user.getEmail())
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
  public UserEntity findByAccountId(String accountId) {
    UserEntity user = userRepository.findUserByAddress(accountId).orElse(null);
    if (Objects.isNull(user)) {
      return userRepository.findByEmail(accountId).orElse(null);
    }
    return user;
  }

  @Override
  public Boolean checkExistEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public void activeUser(String email) {
    UserEntity user = userRepository.findByEmail(email)
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
  public UserResponse editUser(EditUserRequest editUserRequest,
      HttpServletRequest httpServletRequest) {
    log.info("edit user is running...");
    String emailReq = editUserRequest.getEmail();
    String addressReq = editUserRequest.getAddress();
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserEntity user = findByAccountId(accountId);
    String address = "";
    if (Objects.nonNull(emailReq)) {
      if (Boolean.TRUE.equals(checkExistEmail(emailReq))) {
        throw new BusinessException(CommonErrorCode.EMAIL_IS_ALREADY_EXIST);
      }
      user.setEmail(emailReq);
      address = walletRepository.findAddressByUserId(user.getId());
    }
    if (Objects.nonNull(addressReq)) {
      user.setStakeKey(addressReq);
      address = addressReq;
    }
    UserEntity userEdit = userRepository.save(user);
    UserResponse res = userMapper.entityToResponse(userEdit);
    res.setAddress(address);
    return res;
  }

  @Override
  public UserEntity saveUser(String address) {
    UserEntity user = UserEntity.builder()
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
      case ROLE_ADMIN -> {
        RoleEntity rAdmin = roleRepository.findByName(ERole.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
        roles.add(rAdmin);
      }
      case ROLE_USER -> {
        RoleEntity rUser = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
        roles.add(rUser);
      }
      case ROLE_MODERATOR -> {
        RoleEntity rMode = roleRepository.findByName(ERole.ROLE_MODERATOR)
            .orElseThrow(() -> new RuntimeException(CommonErrorCode.ROLE_IS_NOT_FOUND.getDesc()));
        roles.add(rMode);
      }
      default -> {
      }
    }
    return roles;
  }
}
