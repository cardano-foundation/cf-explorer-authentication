package com.sotatek.authservice.service;

import com.sotatek.authservice.model.request.admin.ResetPasswordRequest;
import com.sotatek.authservice.model.response.MessageResponse;

public interface VerifyService {

  /*
   * @author: phuc.nguyen5
   * @since: 09/01/2023
   * description: process verify email
   * @update:
   */
  MessageResponse checkVerifySignUpByEmail(String code);

  /*
   * @author: phuc.nguyen5
   * @since: 12/1/2023
   * description: process add new password
   * @update:
   */
  MessageResponse resetPassword(ResetPasswordRequest resetPasswordRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 12/1/2023
   * description: process confirm reset password
   * @update:
   */
  MessageResponse forgotPassword(String email);
}
