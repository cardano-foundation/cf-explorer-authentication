package com.sotatek.authservice.service;

import com.sotatek.authservice.model.dto.WalletDto;
import com.sotatek.authservice.model.entity.WalletEntity;

public interface WalletService {

  /*
   * @author: phuc.nguyen5
   * @since: 04/11/2022
   * description: update new nonce value in wallet table
   * @update:
   */
  void updateNewNonce(WalletEntity wallet);

  /*
   * @author: phuc.nguyen5
   * @since: 07/11/2022
   * description: get stake address by wallet id
   * @update:
   */
  String getStakeAddressByWalletId(Long walletId);

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: get wallet info by wallet address
   * @update:
   */
  WalletDto getWalletInfo(String stakeAddress);

  /*
   * @author: phuc.nguyen5
   * @since: 10/11/2022
   * description: delete wallet by wallet address
   * @update:
   */
  Boolean deleteWallet(String stakeAddress);
}
