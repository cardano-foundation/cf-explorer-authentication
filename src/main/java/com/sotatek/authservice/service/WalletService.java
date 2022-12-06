package com.sotatek.authservice.service;

import com.sotatek.authservice.model.entity.WalletEntity;

public interface WalletService {

  /*
   * @author: phuc.nguyen5
   * @since: 04/11/2022
   * description: update new nonce value in wallet table
   * @update:
   */
  void updateNewNonce(WalletEntity wallet);
}
