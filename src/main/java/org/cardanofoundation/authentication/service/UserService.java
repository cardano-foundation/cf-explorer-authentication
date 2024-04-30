package org.cardanofoundation.authentication.service;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
  Boolean setTimezoneForUser(String region, HttpServletRequest httpServletRequest);
}
