package org.cardanofoundation.authentication.thread;

import java.util.Locale;

import lombok.extern.log4j.Log4j2;

import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.provider.MailProvider;

@Log4j2
public class MailHandler implements Runnable {

  private MailProvider mailProvider;

  private String email;

  private EUserAction emailType;

  private String code;

  private Locale locale;

  public MailHandler(
      MailProvider mailProvider, String email, EUserAction emailType, Locale locale, String code) {
    this.mailProvider = mailProvider;
    this.email = email;
    this.emailType = emailType;
    this.locale = locale;
    this.code = code;
  }

  @Override
  public void run() {
    try {
      mailProvider.sendVerifyEmail(email, emailType, code, locale);
      Thread.sleep(CommonConstant.SLEEP_TIME);
    } catch (InterruptedException e) {
      log.error("Error: Interrupted thread");
      Thread.currentThread().interrupt();
    }
  }
}
