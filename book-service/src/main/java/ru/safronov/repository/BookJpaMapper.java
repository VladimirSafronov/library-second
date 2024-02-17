package ru.safronov.repository;

import java.util.List;
import java.util.stream.Collectors;
import ru.safronov.service.Book;

public class BookJpaMapper {

  public static Book mapToBook(BookEntity entityRepository) {
    return new Book(entityRepository.getId(), entityRepository.getName());
  }

  public static BookEntity mapToBookEntity(Book book) {
    return new BookEntity(book.getId(), book.getName());
  }

  public static List<Book> mapToBookList(List<BookEntity> entities) {
    return entities.stream()
        .map(BookJpaMapper::mapToBook)
        .collect(Collectors.toList());
  }
}
