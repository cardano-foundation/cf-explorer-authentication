package org.cardanofoundation.authentication.config;

import java.security.PrivateKey;
import java.security.PublicKey;

import jakarta.annotation.PostConstruct;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import org.cardanofoundation.explorer.common.utils.RsaUtils;

@Component
@ConfigurationProperties("rsa.key")
@Getter
@Setter
@RequiredArgsConstructor
public class RsaConfig {

  private String publicAuth;

  private String privateMail;

  private String publicMail;

  private PublicKey publicKeyAuth;

  private PrivateKey privateKeyMail;

  private PublicKey publicKeyMail;

  @PostConstruct
  public void createRsaKey() {
    java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    publicKeyAuth = RsaUtils.getPublicKey(publicAuth);
    privateKeyMail = RsaUtils.getPrivateKey(privateMail);
    publicKeyMail = RsaUtils.getPublicKey(publicMail);
  }
}
