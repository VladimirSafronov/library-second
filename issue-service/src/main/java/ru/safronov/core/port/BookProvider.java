package ru.safronov.core.port;

import ru.safronov.core.domain.Book;

public interface BookProvider {

  Book findById(Long id);
  void save(Book book);

  void deleteAll();
}
