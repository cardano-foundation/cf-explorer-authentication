package com.sotatek.authservice.config;

import com.sotatek.authservice.provider.RsaProvider;
import java.security.PrivateKey;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("rsa.key")
@Getter
@Setter
public class RsaConfig {

  private String rsaKeyFile;

  private PrivateKey rsaKey;

  @Autowired
  private RsaProvider rsaProvider;

  @PostConstruct
  public void createRsaKey() throws Exception {
    java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    rsaKey = rsaProvider.getPrivateKey(rsaKeyFile);
  }
}
