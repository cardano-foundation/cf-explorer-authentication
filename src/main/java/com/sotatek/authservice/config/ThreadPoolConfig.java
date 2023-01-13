package com.sotatek.authservice.config;

import com.sotatek.authservice.config.properties.ThreadPoolProperties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class ThreadPoolConfig {

  private final ThreadPoolProperties threadPool;

  @Bean(name = "sendMailExecutor")
  @Primary
  public ThreadPoolExecutor sendMailExecutor() {
    return new ThreadPoolExecutor(threadPool.getCoreSize(), threadPool.getMaxSize(),
        threadPool.getTimeout(),
        TimeUnit.SECONDS, new ArrayBlockingQueue<>(threadPool.getQueueSize()));
  }
}
