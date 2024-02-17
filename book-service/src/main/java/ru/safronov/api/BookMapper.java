package ru.safronov.api;

import java.util.List;
import java.util.stream.Collectors;
import ru.safronov.api.BookDTO;
import ru.safronov.service.Book;

/**
 * Mapper BookDTO <-> Book
 */
public class BookMapper {

  public static BookDTO mapToDto(Book book) {
    return new BookDTO(book.getId(), book.getName());
  }

  public static Book mapToBook(BookDTO bookDTO) {
    return new Book(bookDTO.getId(), bookDTO.getName());
  }

  public static List<BookDTO> mapToDtoList(List<Book> books) {
    return books.stream()
        .map(BookMapper::mapToDto)
        .collect(Collectors.toList());
  }
}
