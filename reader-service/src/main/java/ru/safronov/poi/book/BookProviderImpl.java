package ru.safronov.poi.book;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import ru.safronov.core.domain.Book;
import ru.safronov.core.port.BookProvider;
import ru.safronov.poi.BaseProvider;

@Service
public class BookProviderImpl extends BaseProvider implements BookProvider {

  public BookProviderImpl(ReactorLoadBalancerExchangeFilterFunction loadBalancer) {
    super(loadBalancer);
  }

  @Override
  public Book findById(Long id) {

    BookDto bookDto = webClient.get()
        .uri("http://book-service/book/" + id)
        .retrieve()
        .bodyToMono(BookDto.class)
        .block();

    assert bookDto != null;
    return BookMapper.mapToBook(bookDto);
  }
}
