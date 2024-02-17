package ru.safronov.library;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.safronov.library.repository.BookEntity;
import ru.safronov.library.repository.BookRepository;
import ru.safronov.library.repository.IssueEntity;
import ru.safronov.library.repository.IssueRepository;
import ru.safronov.library.repository.ReaderEntity;
import ru.safronov.library.repository.ReaderRepository;

@Slf4j
public class IssueControllerTest extends JUnitSpringBootBase {

  @Autowired
  private IssueRepository issueRepository;
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private ReaderRepository readerRepository;
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
      bookRepository.save(new BookEntity((long) i, "Книга №" + i));
      readerRepository.save(new ReaderEntity((long) i, "Читатель №" + i));
    }
    IssueEntity issueEntity = new IssueEntity(1L, 1L, 1L);
    issueEntity.setIssued_at(LocalDateTime.now());
    issueRepository.save(issueEntity);
    IssueEntity issueEntity2 = new IssueEntity(2L, 2L, 2L);
    issueRepository.save(issueEntity2);
  }

  @AfterEach
  void cleanData() {
    bookRepository.deleteAll();
    readerRepository.deleteAll();
    issueRepository.deleteAll();
  }

  @Test
  void returnBookThenSuccess() {
    long notReturnBookId = issueRepository.findAll().stream()
        .filter(it -> it.getReturned_at() == null)
        .findFirst()
        .map(IssueEntity::getId).orElseThrow();
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
    IssueEntity issueEntity = issueRepository.findAll().stream()
        .filter(it -> it.getIssued_at() == null)
        .findFirst().orElseThrow();
    Assertions.assertNotNull(issueEntity);

    JUnitIssueResponse response = webTestClient.post()
        .uri("/issue")
        .bodyValue(issueEntity)
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
    IssueEntity issueEntity = issueRepository.findAll().stream()
        .filter(it -> it.getIssued_at() == null)
        .findFirst().orElseThrow();
    Assertions.assertNotNull(issueEntity);
    readerRepository.findById(issueEntity.getReaderId()).orElseThrow()
        .setBooksCount(Integer.MAX_VALUE);
    issueRepository.save(issueEntity);
    int booksCount = readerRepository.findById(issueEntity.getReaderId()).orElseThrow().getBooksCount();

    JUnitIssueResponse response = webTestClient.post()
        .uri("/issue")
        .bodyValue(issueEntity)
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
    long existIssueId = issueRepository.findAll().stream()
        .filter(it -> it.getId() > 0)
        .findFirst()
        .map(IssueEntity::getId)
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
