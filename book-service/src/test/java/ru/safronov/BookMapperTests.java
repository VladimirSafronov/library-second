package ru.safronov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.safronov.api.BookDTO;
import ru.safronov.api.BookMapper;
import ru.safronov.service.Book;

@ActiveProfiles("test")
public class BookMapperTests {

  Book testBook;
  BookDTO testBookDto;
  private final long testBookId = 100L;
  private final String testBookName = "test book";
  private final int listCount = 5;

  @BeforeEach
  void prepareTests() {
    testBook = new Book(testBookId, testBookName);
    testBookDto = new BookDTO(testBookId, testBookName);
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

  @Test
  void whenMapBookToBookDtoThenSuccess() {
    BookDTO result = BookMapper.mapToDto(testBook);
    assertNotNull(result);
    assertEquals(testBookDto.getId(), result.getId());
    assertEquals(testBookDto.getName(), result.getName());
  }

  @Test
  void whenMapBookWithDiffDataToBookDtoThenError() {
    testBook.setId(testBook.getId() + 2);
    testBook.setName(testBook.getName() + "b");
    BookDTO result = BookMapper.mapToDto(testBook);
    assertNotNull(result);
    assertNotEquals(testBookId, result.getId());
    assertNotEquals(testBookName, result.getName());
  }

  @Test
  void whenMapToListBookDtoThenAllDataEquals() {
    List<Book> testBooks = getTestBooks();
    List<BookDTO> result = BookMapper.mapToDtoList(testBooks);
    assertNotNull(result);
    assertEquals(listCount, result.size());
    for (int i = 0; i < listCount; i++) {
      assertEquals(testBooks.get(i).getId(), result.get(i).getId());
      assertEquals(testBooks.get(i).getName(), result.get(i).getName());
    }
  }

  private List<Book> getTestBooks() {
    List<Book> list = new ArrayList<>();
    for (int i = 1; i <= listCount; i++) {
      list.add(new Book(testBookId + i, testBookName + " #" + i));
    }
    return list;
  }
}
