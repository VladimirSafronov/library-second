package ru.safronov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.safronov.core.domain.Book;
import ru.safronov.poi.book.BookDto;
import ru.safronov.poi.book.BookMapper;

public class BookMapperTests {

  Book testBook;
  BookDto testBookDto;
  private final long testBookId = 100L;
  private final String testBookName = "test book";
  private final int listCount = 5;

  @BeforeEach
  void prepareTests() {
    testBook = new Book(testBookId, testBookName);
    testBookDto = new BookDto(testBookId, testBookName);
  }

  @AfterEach
  void cleanTestData() {
    testBook = null;
    testBookDto = null;
  }

  @Test
  void whenMapBookDtoToBookThenSuccess() {
    Book result = BookMapper.mapToBook(testBookDto);
    assertNotNull(result);
    assertEquals(testBook.getId(), result.getId());
    assertEquals(testBook.getName(), result.getName());
  }

  @Test
  void whenMapBookDtoWithDiffDataToBookThenError() {
    testBookDto.setId(testBookDto.getId() + 1);
    testBookDto.setName(testBookDto.getName() + "a");
    Book result = BookMapper.mapToBook(testBookDto);
    assertNotNull(result);
    assertNotEquals(testBookId, result.getId());
    assertNotEquals(testBookName, result.getName());
  }
}
