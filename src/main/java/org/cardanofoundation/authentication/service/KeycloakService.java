package org.cardanofoundation.authentication.service;

import jakarta.servlet.http.HttpServletRequest;
import org.cardanofoundation.authentication.model.response.UserInfoResponse;

public interface KeycloakService {

  /*
   * @since: 20/09/2023
   * description: check exist email
   * @update:
   */
  Boolean checkExistEmail(String email);

  /*
   * @since: 06/12/2022
   * description: get user info
   * @update:
   */
  UserInfoResponse infoUser(HttpServletRequest httpServletRequest);
}
