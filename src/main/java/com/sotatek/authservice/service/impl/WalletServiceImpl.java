package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.repository.WalletRepository;
import com.sotatek.authservice.service.WalletService;
import com.sotatek.authservice.util.NonceUtils;
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
  public void updateNewNonce(WalletEntity wallet) {
    String nonce = NonceUtils.createNonce();
    String nonceEncode = encoder.encode(nonce);
    wallet.setNonce(nonce);
    wallet.setNonceEncode(nonceEncode);
    wallet.setExpiryDateNonce(Instant.now().plusMillis(nonceExpirationMs));
    walletRepository.save(wallet);
  }
}
