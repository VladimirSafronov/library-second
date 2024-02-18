package ru.safronov;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.safronov.core.domain.Book;
import ru.safronov.core.domain.Issue;
import ru.safronov.core.domain.Reader;
import ru.safronov.core.port.BookProvider;
import ru.safronov.core.port.IssueStorage;
import ru.safronov.core.port.ReaderProvider;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Slf4j
public class IssueControllerTest {

  @Autowired
  private IssueStorage issueStorage;
  @Autowired
  private BookProvider bookProvider;
  @Autowired
  private ReaderProvider readerProvider;
  @Autowired
  private WebTestClient webTestClient;
  private static final int DATA_COUNT = 2;

  @Data
  static class JUnitIssueResponse {

    private Long id;
    private Long bookId;
    private Long readerId;
    private LocalDateTime issued_at;
    private LocalDateTime returned_at;
  }

  @BeforeEach
  void setTestData() {
    for (int i = 1; i <= DATA_COUNT; i++) {
      bookProvider.save(new Book((long) i, "Книга №" + i));
      readerProvider.save(new Reader((long) i, "Читатель №" + i));
    }
    Issue issue = new Issue(1L, 1L, 1L);
    issue.setIssued_at(LocalDateTime.now());
    issueStorage.save(issue);
    Issue issue2 = new Issue(2L, 2L, 2L);
    issueStorage.save(issue2);
  }

  @AfterEach
  void cleanData() {
    bookProvider.deleteAll();
    readerProvider.deleteAll();
    issueStorage.deleteAll();
  }

  @Test
  void returnBookThenSuccess() {
    long notReturnBookId = issueStorage.findAll().stream()
        .filter(it -> it.getReturned_at() == null)
        .findFirst()
        .map(Issue::getId).orElseThrow();
    Assertions.assertTrue(notReturnBookId > 0);
    JUnitIssueResponse response = webTestClient.put()
        .uri("/issue/" + notReturnBookId)
        .exchange()
        .expectStatus().isOk()
        .expectBody(JUnitIssueResponse.class)
        .returnResult().getResponseBody();

    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.returned_at);
    showResponse(response);
  }

  @Test
  void returnBookThenError() {
    JUnitIssueResponse response = webTestClient.put()
        .uri("/issue/-1")
        .exchange()
        .expectStatus().isNotFound()
        .expectBody(JUnitIssueResponse.class)
        .returnResult().getResponseBody();

    Assertions.assertNull(response);
  }

  @Test
  void getBookThenSuccess() {
    Issue issue = issueStorage.findAll().stream()
        .filter(it -> it.getIssued_at() == null)
        .findFirst().orElseThrow();
    Assertions.assertNotNull(issue);

    JUnitIssueResponse response = webTestClient.post()
        .uri("/issue")
        .bodyValue(issue)
        .exchange()
        .expectStatus().isOk()
        .expectBody(JUnitIssueResponse.class)
        .returnResult().getResponseBody();

    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.getIssued_at());
    showResponse(response);
  }

  @Test
  @Disabled
  void getTooMuchBookThenConflict() {
    Issue issue = issueStorage.findAll().stream()
        .filter(it -> it.getIssued_at() == null)
        .findFirst().orElseThrow();
    Assertions.assertNotNull(issue);
    readerProvider.findById(issue.getReaderId()).setBooksCount(Integer.MAX_VALUE);
    issueStorage.save(issue);
    int booksCount = readerProvider.findById(issue.getReaderId()).getBooksCount();

    JUnitIssueResponse response = webTestClient.post()
        .uri("/issue")
        .bodyValue(issue)
        .exchange()
        .expectStatus().is4xxClientError()
        .expectBody(JUnitIssueResponse.class)
        .returnResult().getResponseBody();

    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.getIssued_at());
    showResponse(response);
  }

  @Test
  void getIssueByExistIdThenSuccess() {
    long existIssueId = issueStorage.findAll().stream()
        .filter(it -> it.getId() > 0)
        .findFirst()
        .map(Issue::getId)
        .orElseThrow();

    JUnitIssueResponse responseBody = webTestClient.get()
        .uri("/issue/" + existIssueId)
        .exchange()
        .expectStatus().isOk()
        .expectBody(JUnitIssueResponse.class)
        .returnResult().getResponseBody();

    Assertions.assertNotNull(responseBody);
    Assertions.assertNotNull(responseBody.getId());
  }

  @Test
  void getIssueByNotExistIdThenError() {

    JUnitIssueResponse responseBody = webTestClient.get()
        .uri("/issue/-1")
        .exchange()
        .expectStatus().isNotFound()
        .expectBody(JUnitIssueResponse.class)
        .returnResult().getResponseBody();

    Assertions.assertNull(responseBody);
  }

  private void showResponse(JUnitIssueResponse response) {
    System.out.println("================");
    log.info(response.toString());
    System.out.println("================");
  }

}
