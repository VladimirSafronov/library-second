package ru.safronov.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "readers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(name = "Читатель")
public class Reader {

  private static long sequence = 1L;
  @Id
  @Column(name = "id")
  @Schema(name = "Идентификатор читателя")
  private Long id;
  @Column(name = "name")
  @Schema(name = "Имя читателя")
  private String name;

  @Column(name = "books_count")
  @Schema(name = "Количество книг на руках у читателя")
  private int booksCount;

  public Reader(String name) {
    this.id = sequence++;
    this.name = name;
  }

  public Reader(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Reader() {
  }
}
