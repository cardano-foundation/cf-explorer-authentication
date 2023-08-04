package org.cardanofoundation.authentication.config.email;

import lombok.extern.slf4j.Slf4j;
import org.cardanofoundation.authentication.util.SesSmtpCredentialGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Slf4j

@Profile("ses")
@Configuration
public class AwsEmailConfig {

    @Value("${spring.mail.aws.access-key}")
    private String accessKey;

    @Value("${spring.mail.aws.secret-key}")
    private String secretKey;

    @Value("${spring.mail.aws.region}")
    private String region;

    @Primary
    @Bean
    public JavaMailSender javaMailSender(SesSmtpCredentialGenerator sesSmtpCredentialGenerator) {
        log.info("Creating JavaMailSender");
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(accessKey);
        javaMailSender.setPassword(sesSmtpCredentialGenerator.generateSMTPPassword(secretKey));
        javaMailSender.setHost("email-smtp." + region + ".amazonaws.com");
        javaMailSender.setPort(587);
        return javaMailSender;
    }

}
