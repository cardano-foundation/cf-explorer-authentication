package com.sotatek.authservice.thread;

import com.sotatek.authservice.constant.CommonConstant;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.provider.MailProvider;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MailHandler implements Runnable {

  private MailProvider mailProvider;

  private UserEntity user;

  private EUserAction emailType;

  private String code;

  public MailHandler(MailProvider mailProvider, UserEntity user, EUserAction emailType,
      String code) {
    this.mailProvider = mailProvider;
    this.user = user;
    this.emailType = emailType;
    this.code = code;
  }

  @Override
  public void run() {
    try {
      mailProvider.sendVerifyEmail(user, emailType, code);
      Thread.sleep(CommonConstant.SLEEP_TIME);
    } catch (InterruptedException e) {
      log.error("Error: Interrupted thread");
      Thread.currentThread().interrupt();
    }
  }
}
