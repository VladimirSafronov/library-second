package ru.safronov.library.api.mappers;

import java.util.List;
import java.util.stream.Collectors;
import ru.safronov.library.api.BookDTO;
import ru.safronov.library.model.Book;

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
