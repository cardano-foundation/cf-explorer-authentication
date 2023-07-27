package org.cardanofoundation.authentication.provider;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.config.properties.MailProperties;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class MailProvider {

  @Value("${domain.client}")
  private String domainClient;

  private final JavaMailSender javaMailSender;

  private final MailProperties mail;

  public void sendVerifyEmail(UserEntity user, EUserAction emailType, String code) {

    log.info("From: " + mail.getFrom());
    log.info("Sender: " + mail.getSender());
    log.info("Footer: " + mail.getFooter());
    log.info("Email: " + user.getEmail());
    String contentHtml
        = "Hi there,<br />"
        + "Please click the link below to verify account:<br />"
        + "<h3><a href=\"[URL]\" target=\"_self\">VERIFY</a></h3>"
        + "Thank you,<br />";
    try {
      MimeMessage mailMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mailMessage, Boolean.TRUE);
      helper.setFrom(mail.getFrom(), mail.getSender());
      helper.setTo(user.getEmail());
      StringBuilder verifyURL = new StringBuilder(domainClient);
      switch (emailType) {
        case CREATED -> {
          verifyURL.append("/verify-email?code=").append(code);
          helper.setSubject(mail.getSubjectRegistration());
        }
        case RESET_PASSWORD -> {
          verifyURL.append("/reset-password?code=").append(code);
          helper.setSubject(mail.getSubjectResetPassword());
        }
        default -> {
        }
      }
      contentHtml = contentHtml.replace("[URL]", verifyURL);
      contentHtml = contentHtml + mail.getFooter();
      helper.setText(contentHtml, Boolean.TRUE);
      javaMailSender.send(mailMessage);
    } catch (Exception e) {
      log.error("Error: send mail to verify failure");
      log.error("Error message: " + e, e);
    }
  }
}
