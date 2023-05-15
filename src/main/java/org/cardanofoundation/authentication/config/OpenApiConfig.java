package org.cardanofoundation.authentication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Value("${domain.server}")
  private String domain;

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().servers(Arrays.asList(new Server().url(domain))).info(
        new Info().title("Authentication APIs").description("Sample OpenAPI 3.0").contact(
                new Contact().email("potal.sotatek@sotatek.com").name("Sotatek")
                    .url("https://www.sotatek.com/")).license(
                new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html"))
            .version("1.0.0"));
  }
}
