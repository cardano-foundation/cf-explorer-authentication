package com.sotatek.authservice.config;

import com.sotatek.authservice.provider.RsaProvider;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("rsa.key")
@Getter
@Setter
@RequiredArgsConstructor
public class RsaConfig {

  private String privateKeyAuthPath;

  private String publicKeyAuthPath;

  private String privateKeyMailPath;

  private String publicKeyMailPath;

  private PrivateKey privateKeyAuth;

  private PublicKey publicKeyAuth;

  private PrivateKey privateKeyMail;

  private PublicKey publicKeyMail;

  private final RsaProvider rsaProvider;

  @PostConstruct
  public void createRsaKey() {
    java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    privateKeyAuth = rsaProvider.getPrivateKey(privateKeyAuthPath);
    publicKeyAuth = rsaProvider.getPublicKey(publicKeyAuthPath);
    privateKeyMail = rsaProvider.getPrivateKey(privateKeyMailPath);
    publicKeyMail = rsaProvider.getPublicKey(publicKeyMailPath);
  }
}
