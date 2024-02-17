package ru.safronov.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "books")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(name = "Книга")
public class Book {

  private static long sequence = 1L;

  @Id
  @Column(name = "id")
  @Schema(name = "Идеентификатор книги")
  private Long id;
  @Column(name = "name")
  @Schema(name = "Название книги")
  private String name;

  public Book(String name) {
    this.id = sequence++;
    this.name = name;
  }

  public Book(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Book() {
  }
}
