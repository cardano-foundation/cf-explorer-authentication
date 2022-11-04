package com.sotatek.authservice.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum EWalletAction {
  TRANSFERS("transfers"), RECEIVE("receive"); //Todo add action

  private final String action;
  private static final Map<String, EWalletAction> walletActionMap = Arrays.stream(values())
      .collect(Collectors.toMap(EWalletAction::getAction, Function.identity()));

  EWalletAction(String action) {
    this.action = action;
  }

  public String getAction() {
    return this.action;
  }
}
