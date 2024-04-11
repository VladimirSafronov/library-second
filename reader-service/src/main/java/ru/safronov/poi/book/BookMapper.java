package ru.safronov.poi.book;

import ru.safronov.core.domain.Book;

public class BookMapper {

  public static Book mapToBook(BookDto bookDto) {
    return new Book(bookDto.getId(), bookDto.getName());
  }
}
