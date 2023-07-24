package org.cardanofoundation.authentication.service;

import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.entity.WalletEntity;

public interface WalletService {

  /*
   * @since: 04/11/2022
   * description: update new nonce value in wallet table
   * @update:
   */
  WalletEntity updateNonce(WalletEntity wallet);

  /*
   * @since: 24/02/2023
   * description: save empty wallet
   * @update:
   */
  WalletEntity saveWallet(String address, UserEntity user, String walletName);
}
