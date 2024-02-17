package ru.safronov.api;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Entity класс для работы со слоем api
 */
@Data
@AllArgsConstructor
public class BookDTO {

  private Long id;
  private String name;
}
