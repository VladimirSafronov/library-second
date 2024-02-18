package ru.safronov.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
@Entity
@Table(name = "issues")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(name = "Запись о факте выдачи книги")
public class Issue {

  private static long sequence = 1L;

  @Id
  @Column(name = "id")
  @Schema(name = "Идентификатор записи")
  private Long id;
  @Column(name = "book_id")
  @Schema(name = "Идентификатор взятой книги")
  private Long bookId;
  @Column(name = "reader_id")
  @Schema(name = "Идентификатор взявшего книгу")
  private Long readerId;

  /**
   * Дата выдачи
   */
  @Column(name = "issued_at")
  @Schema(name = "Дата и время получения")
  private LocalDateTime issued_at;
  @Column(name = "returned_at")
  @Schema(name = "Дата и время возврата")
  private LocalDateTime returned_at;

  public Issue(Long bookId, Long readerId) {
    this.id = sequence++;
    this.bookId = bookId;
    this.readerId = readerId;
    this.issued_at = LocalDateTime.now();
  }

  public Issue(Long id, Long bookId, Long readerId, LocalDateTime issued_at) {
    this.id = id;
    this.bookId = bookId;
    this.readerId = readerId;
    this.issued_at = issued_at;
  }

  public Issue(Long id, Long bookId, Long readerId) {
    this.id = id;
    this.bookId = bookId;
    this.readerId = readerId;
  }

  public Issue() {
  }
}
