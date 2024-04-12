package ru.safronov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.safronov.repository.BookEntity;
import ru.safronov.repository.BookJpaMapper;
import ru.safronov.service.Book;

public class BookJpaMapperTests {

  Book testBook;
  BookEntity testBookEntity;
  private final long testBookId = 200L;
  private final String testBookName = "test book";
  private final int listCount = 3;

  @BeforeEach
  void prepareTestData() {
    testBook = new Book(testBookId, testBookName);
    testBookEntity = new BookEntity(testBookId, testBookName);
  }

  @AfterEach
  void cleanTestData() {
    testBook = null;
    testBookEntity = null;
  }

  @Test
  void mapToBookThenAccess() {
    Book result = BookJpaMapper.mapToBook(testBookEntity);
    assertNotNull(result);
    assertEquals(testBookId, result.getId());
    assertEquals(testBookName, result.getName());
  }

  @Test
  void mapToBookWithDiffDataThenError() {
    testBookEntity.setId(testBookEntity.getId() + 1);
    testBookEntity.setName(testBookEntity.getName() + "!");
    Book result = BookJpaMapper.mapToBook(testBookEntity);
    assertNotNull(result);
    assertNotEquals(testBookId, result.getId());
    assertNotEquals(testBookName, result.getName());
  }

  @Test
  void mapToBookEntityThenAccess() {
    BookEntity result = BookJpaMapper.mapToBookEntity(testBook);
    assertNotNull(result);
    assertEquals(testBookId, result.getId());
    assertEquals(testBookName, result.getName());
  }

  @Test
  void mapToBookEntityWithDiffDataThenError() {
    testBook.setId(testBook.getId() + 2);
    testBook.setName(testBook.getName() + ".");
    BookEntity result = BookJpaMapper.mapToBookEntity(testBook);
    assertNotNull(result);
    assertNotEquals(testBookId, result.getId());
    assertNotEquals(testBookName, result.getName());
  }

  @Test
  void mapToListBookThenAllDataEquals() {
    List<BookEntity> bookEntities = getTestBookEntities();
    List<Book> result = BookJpaMapper.mapToBookList(bookEntities);
    assertNotNull(result);
    for (int i = 0; i < listCount; i++) {
      assertEquals(bookEntities.get(i).getId(), result.get(i).getId());
      assertEquals(bookEntities.get(i).getName(), result.get(i).getName());
    }
  }

  private List<BookEntity> getTestBookEntities() {
    List<BookEntity> list = new ArrayList<>();
    for (int i = 1; i <= listCount; i++) {
      list.add(new BookEntity(testBookId + i, testBookName + " #" + i));
    }
    return list;
  }
}
