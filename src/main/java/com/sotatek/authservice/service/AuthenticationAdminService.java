package com.sotatek.authservice.service;

import com.sotatek.authservice.model.request.admin.SignUpAdminRequest;
import com.sotatek.authservice.model.response.MessageResponse;

public interface AuthenticationAdminService {

  MessageResponse signUp(SignUpAdminRequest signUpAdmin);

  MessageResponse checkVerifySignUpByEmail(String code);
}
