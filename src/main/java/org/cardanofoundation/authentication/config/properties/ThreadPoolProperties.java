package org.cardanofoundation.authentication.config.properties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "thread-pool")
@Getter
@Setter
public class ThreadPoolProperties {

  private Integer coreSize;

  private Integer maxSize;

  private Integer queueSize;

  private Integer timeout;
}
