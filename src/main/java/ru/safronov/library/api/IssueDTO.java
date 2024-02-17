package ru.safronov.library.api;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Entity класс для работы со слоем api
 */
@Data
public class IssueDTO {

  private long id;
  private long readerId;
  private long bookId;
  private LocalDateTime issued_at;
  private LocalDateTime returned_at;

  public IssueDTO(long id, long readerId, long bookId, LocalDateTime issued_at) {
    this.id = id;
    this.readerId = readerId;
    this.bookId = bookId;
    this.issued_at = issued_at;
  }
}
