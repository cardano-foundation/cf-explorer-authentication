package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.mapper.WalletMapper;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.request.auth.WalletRequest;
import com.sotatek.authservice.repository.WalletRepository;
import com.sotatek.authservice.service.WalletService;
import com.sotatek.authservice.util.NonceUtils;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import java.util.Optional;
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

  private static final WalletMapper walletMapper = WalletMapper.INSTANCE;

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
  public WalletEntity savaWallet(WalletRequest walletRequest, UserEntity user) {
    String nonce = NonceUtils.createNonce();
    WalletEntity wallet = walletMapper.requestToEntity(walletRequest);
    wallet.setNonce(nonce);
    wallet.setNonceEncode(encoder.encode(nonce));
    wallet.setExpiryDateNonce(Instant.now().plusMillis(nonceExpirationMs));
    wallet.setUser(user);
    return walletRepository.save(wallet);
  }

  @Override
  public WalletEntity findWalletByStakeAddress(String stakeAddress) {
    return walletRepository.findWalletByStakeAddress(stakeAddress)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.WALLET_IS_NOT_EXIST));
  }

  @Override
  public Boolean existsByStakeAddress(String stakeAddress) {
    return walletRepository.existsByStakeAddress(stakeAddress);
  }

  @Override
  public WalletEntity checkTransferWallet(String stakeAddress) {
    Optional<WalletEntity> walletOpt = walletRepository.findWalletByStakeAddress(stakeAddress);
    return walletOpt.orElse(null);
  }
}
