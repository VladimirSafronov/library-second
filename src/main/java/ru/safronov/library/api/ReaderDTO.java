package ru.safronov.library.api;

import lombok.Data;

/**
 * Entity класс для работы со слоем api
 */
@Data
public class ReaderDTO {

  private Long id;
  private String name;

  public ReaderDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
