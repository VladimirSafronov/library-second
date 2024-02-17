package ru.safronov.api;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.safronov.service.Book;

@Service
public class BookProvider {

  private final WebClient webClient;

  public BookProvider() {
    webClient = WebClient.builder().build();
  }

  public Book getBookById() {
    return null;
  }
}
