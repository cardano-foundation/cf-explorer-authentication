package com.sotatek.authservice.service;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
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
   * @since: 22/11/2022
   * description: save wallet
   * @update:
   */
  WalletEntity savaWallet(WalletRequest walletRequest, UserEntity user);

  /*
   * @author: phuc.nguyen5
   * @since: 22/12/2022
   * description: find wallet by stake address
   * @update:
   */
  WalletEntity findWalletByStakeAddress(String stakeAddress);

  /*
   * @author: phuc.nguyen5
   * @since: 22/12/2022
   * description: check exist wallet by stake address
   * @update:
   */
  Boolean existsByStakeAddress(String stakeAddress);

  /*
   * @author: phuc.nguyen5
   * @since: 22/12/2022
   * description: find wallet by stake address
   * @update:
   */
  WalletEntity checkTransferWallet(String stakeAddress);
}
