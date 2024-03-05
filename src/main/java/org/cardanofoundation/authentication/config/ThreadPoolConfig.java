package org.cardanofoundation.authentication.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.cardanofoundation.authentication.config.properties.ThreadPoolProperties;

@Configuration
@RequiredArgsConstructor
public class ThreadPoolConfig {

  private final ThreadPoolProperties threadPool;

  @Bean(name = "sendMailExecutor")
  @Primary
  public ThreadPoolExecutor sendMailExecutor() {
    return new ThreadPoolExecutor(
        threadPool.getCoreSize(),
        threadPool.getMaxSize(),
        threadPool.getTimeout(),
        TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(threadPool.getQueueSize()));
  }
}
