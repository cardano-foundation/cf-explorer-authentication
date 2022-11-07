package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.repository.WalletRepository;
import com.sotatek.authservice.service.UserService;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private WalletRepository walletRepository;


  @Override
  public UserDetails loadUserByUsername(String stakeAddress) throws UsernameNotFoundException {
    WalletEntity wallet = walletRepository.findByStakeAddress(stakeAddress)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    UserEntity user = wallet.getUser();
    return UserDetailsImpl.build(user, wallet);
  }

  @Override
  public String findNonceByAddress(String stakeAddress) {
    WalletEntity wallet = walletRepository.findByStakeAddress(stakeAddress)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_IS_NOT_EXIST));
    return wallet.getNonce();
  }
}
