package ru.safronov.poi.storage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
public class IssueEntity {

  @Id
  private Long id;
  private Long bookId;
  private Long readerId;
  private LocalDateTime issued_at;
  private LocalDateTime returned_at;

  public IssueEntity() {
  }

  public IssueEntity(Long id, Long bookId, Long readerId) {
    this.id = id;
    this.bookId = bookId;
    this.readerId = readerId;
  }
}
