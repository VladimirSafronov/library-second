package ru.safronov.poi.reader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDto {

  private Long id;
  private String name;
  private int booksCount;
}
