package ru.safronov.poi.book;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {

  private Long id;
  private String name;
}
