package org.cardanofoundation.authentication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Value("${domain.server}")
  private String domain;

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .servers(Collections.singletonList(new Server().url(domain)))
        .info(
            new Info()
                .title("Iris Authentication APIs")
                .description("Iris Sample OpenAPI 3.0")
                .contact(
                    new Contact()
                        .email("info@cardano.com")
                        .name("Cardano Foundation - Iris")
                        .url("http://www.cardanofoundation.org"))
                .license(
                    new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                .version("1.0.0"));
  }
}
