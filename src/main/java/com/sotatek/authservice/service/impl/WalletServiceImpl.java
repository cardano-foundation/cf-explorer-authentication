package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.enums.EWalletName;
import com.sotatek.authservice.repository.WalletRepository;
import com.sotatek.authservice.service.WalletService;
import com.sotatek.authservice.util.NonceUtils;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class WalletServiceImpl implements WalletService {

  @Value("${nonce.expirationMs}")
  private Long nonceExpirationMs;

  private final WalletRepository walletRepository;

  private final PasswordEncoder encoder;

  @Override
  public WalletEntity updateNonce(WalletEntity wallet) {
    String nonce = NonceUtils.createNonce();
    String nonceEncode = encoder.encode(nonce);
    wallet.setNonce(nonce);
    wallet.setNonceEncode(nonceEncode);
    wallet.setExpiryDateNonce(Instant.now().plusMillis(nonceExpirationMs));
    return walletRepository.save(wallet);
  }

  @Override
  public WalletEntity findWalletByAddress(String address) {
    return walletRepository.findWalletByAddress(address)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.WALLET_IS_NOT_EXIST));
  }

  @Override
  public WalletEntity saveWallet(String address, UserEntity user, EWalletName walletName) {
    String nonce = NonceUtils.createNonce();
    WalletEntity wallet = WalletEntity.builder().address(address).nonce(nonce)
        .nonceEncode(encoder.encode(nonce))
        .walletName(walletName)
        .expiryDateNonce(Instant.now().plusMillis(nonceExpirationMs)).user(user).build();
    return walletRepository.save(wallet);
  }
}
