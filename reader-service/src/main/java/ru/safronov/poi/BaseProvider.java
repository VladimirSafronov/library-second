package ru.safronov.poi;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.web.reactive.function.client.WebClient;

public class BaseProvider {

  protected final WebClient webClient;
  protected final EurekaClient eurekaClient;
  protected final String serviceName;

  protected BaseProvider(EurekaClient eurekaClient, String serviceName) {
    this.eurekaClient = eurekaClient;
    this.serviceName = serviceName;
    webClient = WebClient.builder().build();
  }

  /**
   * Получить ip-адрес иного сервиса по его названию (ключу)
   */
  protected String getServiceIp(String serviceName) {
    Application application = eurekaClient.getApplication(serviceName);
    List<InstanceInfo> instances = application.getInstances();
    int randomInd = ThreadLocalRandom.current().nextInt(instances.size());
    return instances.get(randomInd).getHomePageUrl();
  }
}
