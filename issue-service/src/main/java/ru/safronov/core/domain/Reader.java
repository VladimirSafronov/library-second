package ru.safronov.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reader {

  private Long id;
  private String name;
  private int booksCount;

  public Reader(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
