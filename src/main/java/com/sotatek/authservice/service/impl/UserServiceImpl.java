package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.mapper.UserMapper;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.UserHistoryEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.request.user.EditUserRequest;
import com.sotatek.authservice.model.response.UserInfoResponse;
import com.sotatek.authservice.model.response.UserResponse;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.repository.BookMarkRepository;
import com.sotatek.authservice.repository.PrivateNoteRepository;
import com.sotatek.authservice.repository.UserHistoryRepository;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.repository.WalletRepository;
import com.sotatek.authservice.service.UserHistoryService;
import com.sotatek.authservice.service.UserService;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  private static final UserMapper userMapper = UserMapper.INSTANCE;

  @Override
  public UserDetails loadUserByUsername(String stakeAddress) throws UsernameNotFoundException {
    WalletEntity wallet = walletRepository.findByStakeAddress(stakeAddress)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    UserEntity user = wallet.getUser();
    return UserDetailsImpl.build(user, wallet);
  }

  @Override
  public String findNonceByAddress(String address) {
    WalletEntity wallet = walletRepository.findByStakeAddress(address)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    return wallet.getNonce();
  }

  @Override
  public Boolean checkExistUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Transactional(rollbackFor = {RuntimeException.class})
  @Override
  public UserResponse editUser(EditUserRequest editUserRequest,
      HttpServletRequest httpServletRequest) {
    log.info("edit user is running...");
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    user.setEmail(editUserRequest.getEmail());
    user.setAvatar(editUserRequest.getAvatar());
    UserEntity userEdit = userRepository.save(user);
    userHistoryService.saveUserHistory(EUserAction.UPDATED, editUserRequest.getIpAddress(),
        Instant.now(), true, userEdit);
    return userMapper.entityToResponse(userEdit);
  }

  @Override
  public UserInfoResponse infoUser(HttpServletRequest httpServletRequest) {
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    String walletId = jwtProvider.getWalletIdFromJwtToken(token);
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    Integer sizeBookMark = bookMarkRepository.getCountBookMarkByUser(user.getId());
    Integer sizeNote = noteRepository.getCountNoteByUser(user.getId());
    String address = walletRepository.getAddressWalletById(Long.valueOf(walletId));
    UserHistoryEntity userHistory = userHistoryRepository.findFirstByUserAndUserActionOrderByActionTimeDesc(
        user, EUserAction.LOGIN);
    return UserInfoResponse.builder().username(username).email(user.getEmail())
        .avatar(user.getAvatar()).sizeBookmark(sizeBookMark).sizeNote(sizeNote).wallet(address)
        .lastLogin(userHistory.getActionTime())
        .build();
  }
}
