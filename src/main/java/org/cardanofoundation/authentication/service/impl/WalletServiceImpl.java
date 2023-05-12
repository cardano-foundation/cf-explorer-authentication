package org.cardanofoundation.authentication.service.impl;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.entity.WalletEntity;
import org.cardanofoundation.authentication.model.enums.EWalletName;
import org.cardanofoundation.authentication.repository.WalletRepository;
import org.cardanofoundation.authentication.service.WalletService;
import org.cardanofoundation.authentication.util.NonceUtils;
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
  public WalletEntity saveWallet(String address, UserEntity user, EWalletName walletName) {
    String nonce = NonceUtils.createNonce();
    WalletEntity wallet = WalletEntity.builder().address(address).nonce(nonce)
        .nonceEncode(encoder.encode(nonce))
        .walletName(walletName)
        .expiryDateNonce(Instant.now().plusMillis(nonceExpirationMs)).user(user).build();
    return walletRepository.save(wallet);
  }
}
