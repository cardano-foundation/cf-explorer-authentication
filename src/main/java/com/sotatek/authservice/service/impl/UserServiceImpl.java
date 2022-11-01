package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.security.UserDetailsImpl;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.service.UserService;
import com.sotatek.authservice.util.NonceUtils;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Value("${nonce.expirationMs}")
  private Long nonceExpirationMs;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder encoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByUsername(username).orElseThrow(
        () -> BusinessException.builder()
            .errorCode(CommonErrorCode.USER_IS_NOT_EXIST.getServiceErrorCode())
            .errorMsg(CommonErrorCode.USER_IS_NOT_EXIST.getDesc() + " with username: " + username)
            .build());
    return UserDetailsImpl.build(user);
  }

  @Override
  public String findNonceByAddress(String publicAddress) {
    UserEntity user = userRepository.findByPublicAddress(publicAddress).orElseThrow(
        () -> BusinessException.builder()
            .errorCode(CommonErrorCode.USER_IS_NOT_EXIST.getServiceErrorCode()).errorMsg(
                CommonErrorCode.USER_IS_NOT_EXIST.getDesc() + " with address: " + publicAddress)
            .build());
    return user.getNonce();
  }

  @Override
  public void updateNewNonce(UserEntity user) {
    String nonce = NonceUtils.createNonce();
    String nonceEncode = encoder.encode(nonce);
    user.setNonce(nonce);
    user.setNonceEncode(nonceEncode);
    user.setExpiryDateNonce(Instant.now().plusMillis(nonceExpirationMs));
    userRepository.save(user);
  }
}
