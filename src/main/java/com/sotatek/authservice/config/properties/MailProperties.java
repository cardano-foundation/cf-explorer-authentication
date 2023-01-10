package com.sotatek.authservice.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConfigurationProperties(prefix = "mail")
@Getter
@Setter
@Primary
public class MailProperties {

  private String from;

  private String sender;

  private String subjectRegistration;

  private String subjectResetPassword;

  private String footer;

  private Long expirationMs;
}
