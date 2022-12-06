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

  private String privateKeyFile;

  private String publicKeyFile;

  private PrivateKey privateKey;

  private PublicKey publicKey;

  private final RsaProvider rsaProvider;

  @PostConstruct
  public void createRsaKey() {
    java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    privateKey = rsaProvider.getPrivateKey(privateKeyFile);
    publicKey = rsaProvider.getPublicKey(publicKeyFile);
  }
}
