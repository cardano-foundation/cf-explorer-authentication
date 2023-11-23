package org.cardanofoundation.authentication.provider;

import jakarta.mail.internet.MimeMessage;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.config.properties.MailProperties;
import org.cardanofoundation.authentication.constant.CommonConstant;
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

  private final LocaleProvider localeProvider;

  public void sendVerifyEmail(String email, EUserAction emailType, String code, Locale locale) {
    log.info("start send verify mail to: " + email);
    String contentHtml = localeProvider.getValue("mail.base-content", locale);
    try {
      MimeMessage mailMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mailMessage, Boolean.TRUE);
      helper.setFrom(mail.getFrom(), localeProvider.getValue("mail.sender", locale));
      helper.setTo(email);
      StringBuilder verifyURL = new StringBuilder(domainClient);
      /* Config multiple language for mail
      if (locale.equals(new Locale("en"))) {
        verifyURL.append(CommonConstant.ENGLISH_URL);
      } else if (locale.equals(new Locale("fr"))) {
        verifyURL.append(CommonConstant.FRENCH_URL);
      }
      */
      switch (emailType) {
        case CREATED -> {
          verifyURL.append("/verify-email?code=").append(code);
          helper.setSubject(localeProvider.getValue("mail.subject-registration", locale));
        }
        case RESET_PASSWORD -> {
          verifyURL.append("/reset-password?code=").append(code);
          helper.setSubject(localeProvider.getValue("mail.subject-reset-password", locale));
        }
        default -> log.error("Mail type incorrect: " + emailType);
      }
      contentHtml = contentHtml.replace("[URL]", verifyURL);
      contentHtml = contentHtml + localeProvider.getValue("mail.footer", locale);
      helper.setText(contentHtml, Boolean.TRUE);
      javaMailSender.send(mailMessage);
    } catch (Exception e) {
      log.error("Failed to send verification email", e);
    }
  }
}
