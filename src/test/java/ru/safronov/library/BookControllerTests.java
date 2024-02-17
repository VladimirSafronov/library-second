package ru.safronov.library;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.safronov.library.repository.BookEntity;
import ru.safronov.library.repository.BookRepository;

public class BookControllerTests extends JUnitSpringBootBase {

  @Autowired
  private WebTestClient webTestClient;
  @Autowired
  private BookRepository bookRepository;
  private static final int DATA_COUNT = 3;

  @Data
  static class JUnitBookResponse {

    private Long id;
    private String name;
  }

  @BeforeEach
  void setTestData() {
    for (int i = 1; i <= DATA_COUNT; i++) {
      BookEntity book = new BookEntity((long) i, "Книга №" + i);
      bookRepository.save(book);
    }
  }

  @AfterEach
  void cleanTestData() {
    bookRepository.deleteAll();
  }

  @Test
  void getAllBooksTest() {
    List<BookEntity> expectedList = bookRepository.findAll();

    List<JUnitBookResponse> responseBody = webTestClient.get()
        .uri("/book")
        .exchange()
        .expectStatus().isOk()
        .expectBody(new ParameterizedTypeReference<List<JUnitBookResponse>>() {
        })
        .returnResult()
        .getResponseBody();

    assert responseBody != null;
    Assertions.assertEquals(expectedList.size(), responseBody.size());
    isResponseBodyEqualList(responseBody, expectedList);
  }

  @Test
  void getBookByIdSuccess() {
    Random random = new Random();
    long randomId = random.nextLong(1, DATA_COUNT + 1);
    BookEntity bookExpected = bookRepository.findById(randomId).orElse(new BookEntity(0L, ""));

    JUnitBookResponse response = webTestClient
        .get()
        .uri("/book/" + randomId)
        .exchange()
        .expectStatus().isOk()
        .expectBody(JUnitBookResponse.class)
        .returnResult()
        .getResponseBody();
    Assertions.assertNotNull(response);
    Assertions.assertEquals(bookExpected.getId(), response.getId());
    Assertions.assertEquals(bookExpected.getName(), response.getName());
  }

  @Test
  void getBookByNotExistsIdThenError() {
    webTestClient.get()
        .uri("/book/-1")
        .exchange()
        .expectStatus().isNotFound();
  }

  @Test
  void deleteBookByIdSuccess() {
    Random random = new Random();
    long randomId = random.nextLong(1, DATA_COUNT + 1);

    List<BookEntity> allBooks = bookRepository.findAll();
    List<JUnitBookResponse> responses = webTestClient.delete()
        .uri("/book/" + randomId)
        .exchange()
        .expectStatus().isOk()
        .expectBody(new ParameterizedTypeReference<List<JUnitBookResponse>>() {
        })
        .returnResult()
        .getResponseBody();

    Assertions.assertNotNull(allBooks);
    assert responses != null;
    Assertions.assertEquals(allBooks.size() - 1, responses.size());
    allBooks.remove(findIndex(allBooks, randomId));
    isResponseBodyEqualList(responses, allBooks);
  }

  @Test
  void createBookThenSuccess() {
    String bookName = "createdBook";

    JUnitBookResponse response = webTestClient.post()
        .uri("/book?name=" + bookName)
        .exchange()
        .expectStatus().isCreated()
        .expectBody(JUnitBookResponse.class)
        .returnResult().getResponseBody();

    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.getId());
    Assertions.assertTrue(bookRepository.findById(response.getId()).isPresent());
  }

  /**
   * Метод находит по какому индексу в списке находится сущность с искомым id
   */
  private int findIndex(List<BookEntity> list, Long id) {
    for (int i = 0; i < list.size(); i++) {
      if (Objects.equals(list.get(i).getId(), id)) {
        return i;
      }
    }
    return -1;
  }

  private void isResponseBodyEqualList(List<JUnitBookResponse> responses, List<BookEntity> books) {
    for (JUnitBookResponse response : responses) {
      boolean found = books.stream()
          .filter(it -> Objects.equals(it.getId(), response.getId()))
          .anyMatch(it -> Objects.equals(it.getName(), response.getName()));
      Assertions.assertTrue(found);
    }
  }
}
