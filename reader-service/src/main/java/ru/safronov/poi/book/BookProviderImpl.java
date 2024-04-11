package ru.safronov.poi.book;

import com.netflix.discovery.EurekaClient;
import org.springframework.stereotype.Service;
import ru.safronov.core.domain.Book;
import ru.safronov.core.port.BookProvider;
import ru.safronov.poi.BaseProvider;

@Service
public class BookProviderImpl extends BaseProvider implements BookProvider {

  public BookProviderImpl(EurekaClient eurekaClient) {
    super(eurekaClient, "BOOK-SERVICE");
  }

  @Override
  public Book findById(Long id) {

    BookDto bookDto = webClient.get()
        .uri(getServiceIp(serviceName) + "/book/" + id)
        .retrieve()
        .bodyToMono(BookDto.class)
        .block();

    assert bookDto != null;
    return BookMapper.mapToBook(bookDto);
  }
}
