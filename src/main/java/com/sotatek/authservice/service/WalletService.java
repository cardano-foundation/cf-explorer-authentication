package com.sotatek.authservice.service;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.enums.EWalletName;
import com.sotatek.authservice.model.request.auth.WalletRequest;

public interface WalletService {

  /*
   * @author: phuc.nguyen5
   * @since: 04/11/2022
   * description: update new nonce value in wallet table
   * @update:
   */
  WalletEntity updateNonce(WalletEntity wallet);

  /*
   * @author: phuc.nguyen5
   * @since: 22/12/2022
   * description: find wallet by stake address
   * @update:
   */
  WalletEntity findWalletByAddress(String address);

  /*
   * @author: phuc.nguyen5
   * @since: 24/02/2023
   * description: save empty wallet
   * @update:
   */
  WalletEntity saveWallet(String address, UserEntity user, EWalletName walletName);
}
