package ru.safronov;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.safronov.repository.ReaderEntity;
import ru.safronov.repository.ReaderRepository;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ReaderControllerTests {

  @Autowired
  private WebTestClient webTestClient;
  @Autowired
  private ReaderRepository readerRepository;
  private final int TEST_DATA = 3;
  private final Random random = new Random();

  @Data
  static class JUnitReaderResponse {

    private Long id;
    private String name;
  }

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
    for (int i = 1; i <= TEST_DATA; i++) {
      readerRepository.save(new ReaderEntity((long) i, "Читатель №" + i));
    }
  }

  @AfterEach
  void cleanTestData() {
    readerRepository.deleteAll();
  }

  @Test
  void getReaderByExistIdThenSuccess() {
    long existReaderId = random.nextLong(1, TEST_DATA + 1);

    JUnitReaderResponse responseBody = webTestClient.get()
        .uri("/reader/" + existReaderId)
        .exchange()
        .expectStatus().isOk()
        .expectBody(JUnitReaderResponse.class)
        .returnResult().getResponseBody();

    Assertions.assertNotNull(responseBody);
    Assertions.assertTrue(responseBody.getName().endsWith(String.valueOf(existReaderId)));
  }

  @Test
  void getReaderByNotExistIdThenError() {

    JUnitReaderResponse responseBody = webTestClient.get()
        .uri("/reader/-1")
        .exchange()
        .expectStatus().isNotFound()
        .expectBody(JUnitReaderResponse.class)
        .returnResult().getResponseBody();

    Assertions.assertNull(responseBody);
  }

  @Test
  void createReaderThanSuccess() {
    String readerName = "createdReader";

    JUnitReaderResponse response = webTestClient.post()
        .uri("/reader?name=" + readerName)
        .exchange()
        .expectStatus().isCreated()
        .expectBody(JUnitReaderResponse.class)
        .returnResult().getResponseBody();

    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.getId());
    Assertions.assertTrue(readerRepository.findById(response.getId()).isPresent());
  }

  @Test
  void deleteReaderByExistsIdThenSuccess() {
    long existReaderId = random.nextLong(1, TEST_DATA + 1);
    List<ReaderEntity> readers = readerRepository.findAll();

    List<JUnitReaderResponse> afterDeletingList = webTestClient.delete()
        .uri("/reader/" + existReaderId)
        .exchange()
        .expectStatus().isOk()
        .expectBody(new ParameterizedTypeReference<List<JUnitReaderResponse>>() {
        })
        .returnResult().getResponseBody();

    Assertions.assertNotNull(afterDeletingList);
    Assertions.assertEquals(readers.size() - 1, afterDeletingList.size());
  }
}
