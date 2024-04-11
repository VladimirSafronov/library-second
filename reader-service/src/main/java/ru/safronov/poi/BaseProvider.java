package ru.safronov.poi;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

public class BaseProvider {

  protected final WebClient webClient;

  protected BaseProvider(
      ReactorLoadBalancerExchangeFilterFunction loadBalancer) {
    webClient = WebClient.builder()
        .filter(loadBalancer)
        .build();
  }
}
