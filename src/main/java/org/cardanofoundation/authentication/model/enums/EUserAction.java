package org.cardanofoundation.authentication.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum EUserAction {
  CREATED("Created"),
  RESET_PASSWORD("Reset password");

  private final String action;
  private static final Map<String, EUserAction> userActionMap =
      Arrays.stream(values())
          .collect(Collectors.toMap(EUserAction::getAction, Function.identity()));

  EUserAction(String action) {
    this.action = action;
  }
}
