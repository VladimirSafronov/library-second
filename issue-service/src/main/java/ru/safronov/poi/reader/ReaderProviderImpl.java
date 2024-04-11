package ru.safronov.poi.reader;

import com.netflix.discovery.EurekaClient;
import org.springframework.stereotype.Service;
import ru.safronov.core.domain.Reader;
import ru.safronov.core.port.ReaderProvider;
import ru.safronov.poi.BaseProvider;

@Service
public class ReaderProviderImpl extends BaseProvider implements ReaderProvider {

  public ReaderProviderImpl(EurekaClient eurekaClient) {
    super(eurekaClient, "READER-SERVICE");
  }

  @Override
  public Reader findById(Long id) {

    ReaderDto readerDto = webClient.get()
        .uri(getServiceIp(serviceName) + "/reader/" + id)
        .retrieve()
        .bodyToMono(ReaderDto.class)
        .block();

    assert readerDto != null;
    return ReaderMapper.mapToReader(readerDto);
  }

  @Override
  public void save(Reader reader) {
    ReaderDto readerDto = ReaderMapper.mapToReaderDto(reader);
    webClient.patch()
        .uri(getServiceIp(serviceName) + "/reader")
        .bodyValue(readerDto)
        .retrieve()
        .toEntity(ReaderDto.class)
        .block();
  }

  @Override
  public void deleteAll() {
    webClient.delete()
        .uri(getServiceIp(serviceName) + "/reader")
        .retrieve()
        .toBodilessEntity()
        .block();
  }

}
