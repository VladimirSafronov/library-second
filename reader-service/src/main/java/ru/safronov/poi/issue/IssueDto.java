package ru.safronov.poi.issue;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IssueDto {

  private Long id;
  private Long bookId;
  private Long readerId;
  private LocalDateTime issued_at;
  private LocalDateTime returned_at;

  public IssueDto(Long id, Long bookId, Long readerId, LocalDateTime issued_at) {
    this.id = id;
    this.bookId = bookId;
    this.readerId = readerId;
    this.issued_at = issued_at;
  }
}
