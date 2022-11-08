package com.sotatek.authservice.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum EUserAction {
  CREATED("created"), UPDATED("updated"), DELETED("deleted"), LOGIN("login"), LOGOUT(
      "logout"), TRANSFERS_WALLET("transfers_wallet");

  private final String action;
  private static final Map<String, EUserAction> userActionMap = Arrays.stream(values())
      .collect(Collectors.toMap(EUserAction::getAction, Function.identity()));

  EUserAction(String action) {
    this.action = action;
  }

  public String getAction() {
    return this.action;
  }
}
