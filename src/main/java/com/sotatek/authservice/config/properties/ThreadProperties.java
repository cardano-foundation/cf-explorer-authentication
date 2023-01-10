package com.sotatek.authservice.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConfigurationProperties(prefix = "thread")
@Getter
@Setter
@Primary
public class ThreadProperties {

  private Integer coreSize;

  private Integer maxSize;

  private Integer queueSize;

  private Integer timeout;
}
