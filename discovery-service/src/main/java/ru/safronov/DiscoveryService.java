package ru.safronov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Service-discovery pattern. Его задача хранить адреса и регистрировать сервисы. Когда запускается
 * сервис, он регистрируется здесь. Если какой-то сервис меняет адрес, остальные сервисы
 * автоматически узнают о нем
 */
@EnableEurekaServer
@SpringBootApplication
public class DiscoveryService {

  public static void main(String[] args) {
    SpringApplication.run(DiscoveryService.class, args);
  }
}