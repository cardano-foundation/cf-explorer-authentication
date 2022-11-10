package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.mapper.WalletMapper;
import com.sotatek.authservice.model.dto.WalletDto;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.repository.WalletRepository;
import com.sotatek.authservice.service.WalletService;
import com.sotatek.authservice.util.NonceUtils;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

  @Value("${nonce.expirationMs}")
  private Long nonceExpirationMs;

  @Autowired
  private WalletRepository walletRepository;

  @Autowired
  private PasswordEncoder encoder;

  private WalletMapper walletMapper = WalletMapper.INSTANCE;

  @Override
  public void updateNewNonce(WalletEntity wallet) {
    String nonce = NonceUtils.createNonce();
    String nonceEncode = encoder.encode(nonce);
    wallet.setNonce(nonce);
    wallet.setNonceEncode(nonceEncode);
    wallet.setExpiryDateNonce(Instant.now().plusMillis(nonceExpirationMs));
    walletRepository.save(wallet);
  }

  @Override
  public String getStakeAddressByWalletId(Long walletId) {
    WalletEntity wallet = walletRepository.findById(walletId)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
    return wallet.getStakeAddress();
  }

  @Override
  public WalletDto getWalletInfo(String stakeAddress) {
    WalletEntity wallet = walletRepository.findByStakeAddressAndIsDeletedFalse(stakeAddress)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
    return walletMapper.entityToDto(wallet);
  }

  @Override
  public Boolean deleteWallet(String stakeAddress) {
    WalletEntity wallet = walletRepository.findByStakeAddressAndIsDeletedFalse(stakeAddress)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
    wallet.setDeleted(true);
    walletRepository.save(wallet);
    return true;
  }
}
