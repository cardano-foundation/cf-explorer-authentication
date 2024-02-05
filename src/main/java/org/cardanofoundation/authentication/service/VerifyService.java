package org.cardanofoundation.authentication.service;

import jakarta.servlet.http.HttpServletRequest;

import org.cardanofoundation.authentication.model.request.auth.ResetPasswordRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;

public interface VerifyService {

  /*
   * @since: 09/01/2023
   * description: process verify email
   * @update:
   */
  MessageResponse checkVerifySignUpByEmail(String code);

  /*
   * @since: 12/1/2023
   * description: process add new password
   * @update:
   */
  MessageResponse resetPassword(ResetPasswordRequest resetPasswordRequest);

  /*
   * @since: 12/1/2023
   * description: process confirm reset password
   * @update:
   */
  MessageResponse forgotPassword(String email, HttpServletRequest httpServletRequest);

  /*
   * @since: 19/07/2023
   * description: check expired verify email
   * @update:
   */
  Boolean checkExpiredCode(String code);
}
