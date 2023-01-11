package com.sotatek.authservice.provider;

import com.sotatek.authservice.config.properties.MailProperties;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.EUserAction;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

  private final JavaMailSender mailSender;

  private final MailProperties mail;

  public void sendVerifyEmail(UserEntity user, EUserAction emailType, String code) {
    log.info("start send verify mail to: " + user.getUsername());
    String contentHtml
        = "Hi there,<br />"
        + "Please click the link below to verify account:<br />"
        + "<h3><a href=\"[URL]\" target=\"_self\">VERIFY</a></h3>"
        + "Thank you,<br />";
    try {
      MimeMessage mailMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mailMessage, Boolean.TRUE);
      helper.setFrom(mail.getFrom(), mail.getSender());
      helper.setTo(user.getEmail());
      helper.setSubject(mail.getSubjectRegistration());
      StringBuilder verifyURL = new StringBuilder(domainClient);
      switch (emailType) {
        case CREATED:
          verifyURL.append("/login?code=").append(code);
          break;
        case RESET_PASSWORD:
          verifyURL.append("/reset-password?code=").append(code);
          break;
        default:
          break;
      }
      contentHtml = contentHtml.replace("[URL]", verifyURL);
      contentHtml = contentHtml + mail.getFooter();
      helper.setText(contentHtml, Boolean.TRUE);
      mailSender.send(mailMessage);
    } catch (Exception e) {
      log.error("Error: send mail to verify failure");
      log.error("Error message: " + e);
    }
  }
}