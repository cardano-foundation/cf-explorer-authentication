package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.request.user.DeleteUserRequest;
import com.sotatek.authservice.model.request.user.EditUserRequest;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.repository.WalletRepository;
import com.sotatek.authservice.service.UserHistoryService;
import com.sotatek.authservice.service.UserService;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private WalletRepository walletRepository;

  @Autowired
  private UserHistoryService userHistoryService;


  @Override
  public UserDetails loadUserByUsername(String stakeAddress) throws UsernameNotFoundException {
    WalletEntity wallet = walletRepository.findByStakeAddressAndIsDeletedFalse(stakeAddress)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    UserEntity user = wallet.getUser();
    return UserDetailsImpl.build(user, wallet);
  }

  @Override
  public String findNonceByAddress(String stakeAddress) {
    WalletEntity wallet = walletRepository.findByStakeAddressAndIsDeletedFalse(stakeAddress)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    return wallet.getNonce();
  }

  @Override
  public Boolean checkExistUsername(String username) {
    return userRepository.existsByUsernameAndIsDeletedFalse(username);
  }

  @Transactional(rollbackFor = {RuntimeException.class})
  @Override
  public Boolean editUser(EditUserRequest editUserRequest, String username) {
    log.info("edit user is running...");
    UserEntity user = userRepository.findByUsernameAndIsDeletedFalse(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    user.setUsername(editUserRequest.getUsername());
    user.setPhone(editUserRequest.getPhone());
    user.setEmail(editUserRequest.getEmail());
    user.setAvatar(editUserRequest.getAvatar());
    UserEntity userEdit = userRepository.save(user);
    userHistoryService.saveUserHistory(EUserAction.UPDATED, editUserRequest.getIpAddress(),
        Instant.now(), true, userEdit);
    return true;
  }

  @Transactional(rollbackFor = {RuntimeException.class})
  @Override
  public Boolean deleteUser(DeleteUserRequest deleteUserRequest) {
    log.info("delete user is running...");
    String username = deleteUserRequest.getUsername();
    UserEntity user = userRepository.findByUsernameAndIsDeletedFalse(username)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    List<WalletEntity> wallets = walletRepository.findAllByUserId(user.getId());
    user.setDeleted(true);
    userRepository.save(user);
    if (wallets != null) {
      wallets.forEach(wallet -> {
        wallet.setDeleted(true);
        walletRepository.save(wallet);
      });
    }
    userHistoryService.saveUserHistory(EUserAction.DELETED, deleteUserRequest.getIpAddress(),
        Instant.now(), true, user);
    return true;
  }
}
