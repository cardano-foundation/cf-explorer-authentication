package com.sotatek.authservice.controller;

import com.sotatek.authservice.model.dto.WalletDto;
import com.sotatek.authservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

  @Autowired
  private WalletService walletService;

  @GetMapping("/info/{stakeAddress}")
  public ResponseEntity<WalletDto> walletInfo(@PathVariable String stakeAddress) {
    return ResponseEntity.ok(walletService.getWalletInfo(stakeAddress));
  }

  @DeleteMapping("/delete/{stakeAddress}")
  public ResponseEntity<Boolean> delete(@PathVariable String stakeAddress) {
    return ResponseEntity.ok(walletService.deleteWallet(stakeAddress));
  }

}
