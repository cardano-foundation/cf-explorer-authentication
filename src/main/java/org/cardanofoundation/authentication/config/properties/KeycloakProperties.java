package org.cardanofoundation.authentication.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak")
@Getter
@Setter
public class KeycloakProperties {

  private String realm;

  private String  authServerUrl;

  private String sslRequired;

  private String resource;

  private Credentials credentials;

  private Boolean useResourceRoleMappings;

  private Boolean bearerOnly;

  @Getter
  @Setter
  public static class Credentials {

    private String secret;
  }
}
