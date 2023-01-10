package com.sotatek.authservice.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum EUserAction {
  CREATED("Created"), UPDATED("Updated"), LOGIN("Login"), LOGOUT("Logout"), TRANSFERS_WALLET(
      "Transfers wallet"), ADD_BOOKMARK("Add bookmark"), REMOVE_BOOKMARK(
      "Remove bookmark"), ADD_PRIVATE_NOTE("Add private note"), EDIT_PRIVATE_NOTE(
      "Edit private note"), REMOVE_PRIVATE_NOTE("Remove private note"), RESET_PASSWORD(
      "Reset password admin");

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
