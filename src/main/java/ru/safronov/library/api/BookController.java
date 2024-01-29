package ru.safronov.library.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.safronov.library.model.Book;
import ru.safronov.library.service.BookService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/book")
@Tag(name = "Book")
public class BookController {

  private final BookService bookService;

  @GetMapping
  @Operation(summary = "get all books", description = "Загружает все книги в библиотеке")
  public ResponseEntity<List<Book>> getAllBooks() {
    return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
  }

  @GetMapping(path = "/{id}")
  @Operation(summary = "get book by id", description = "Находит книгу по ее идентификатору")
  public ResponseEntity<Book> getBookInfo(@PathVariable Long id) {
    return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
  }

  @DeleteMapping(path = "/{id}")
  @Operation(summary = "delete book", description = "Удаляет книгу из библиотеки")
  public ResponseEntity<List<Book>> deleteBook(@PathVariable Long id) {
    final List<Book> books;
    try {
      books = bookService.deleteBook(id);
    } catch (NoSuchElementException ex) {
      return ResponseEntity.notFound().build();
    }
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @PostMapping
  @Operation(summary = "add book", description = "Добавляет новую книгу в библиотеку")
  public ResponseEntity<Book> createBook(@RequestParam String name) {
    Book book = bookService.addBook(name);
    return new ResponseEntity<>(book, HttpStatus.CREATED);
  }
}
