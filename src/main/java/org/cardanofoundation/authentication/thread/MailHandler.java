package org.cardanofoundation.authentication.thread;

import java.util.Locale;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.provider.MailProvider;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MailHandler implements Runnable {

  private MailProvider mailProvider;

  private UserEntity user;

  private EUserAction emailType;

  private String code;

  private Locale locale;

  public MailHandler(MailProvider mailProvider, UserEntity user, EUserAction emailType, Locale locale,
      String code) {
    this.mailProvider = mailProvider;
    this.user = user;
    this.emailType = emailType;
    this.locale = locale;
    this.code = code;
  }

  @Override
  public void run() {
    try {
      mailProvider.sendVerifyEmail(user, emailType, code, locale);
      Thread.sleep(CommonConstant.SLEEP_TIME);
    } catch (InterruptedException e) {
      log.error("Error: Interrupted thread");
      Thread.currentThread().interrupt();
    }
  }
}
