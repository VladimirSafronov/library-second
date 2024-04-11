package ru.safronov.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderEntity {

  @Id
  private Long id;
  private String name;
  private int booksCount;

  public ReaderEntity(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
