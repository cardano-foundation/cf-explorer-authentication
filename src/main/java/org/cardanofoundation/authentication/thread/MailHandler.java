package org.cardanofoundation.authentication.thread;

import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.provider.MailProvider;
import org.keycloak.representations.idm.UserRepresentation;

@Log4j2
public class MailHandler implements Runnable {

  private MailProvider mailProvider;

  private String email;

  private EUserAction emailType;

  private String code;

  public MailHandler(MailProvider mailProvider, String email, EUserAction emailType,
      String code) {
    this.mailProvider = mailProvider;
    this.email = email;
    this.emailType = emailType;
    this.code = code;
  }

  @Override
  public void run() {
    try {
      mailProvider.sendVerifyEmail(email, emailType, code);
      Thread.sleep(CommonConstant.SLEEP_TIME);
    } catch (InterruptedException e) {
      log.error("Error: Interrupted thread");
      Thread.currentThread().interrupt();
    }
  }
}
