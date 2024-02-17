package ru.safronov.library.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.safronov.library.model.Book;
import ru.safronov.library.repository.BookRepository;
import ru.safronov.library.repository.mappers.BookJpaMapper;

@Slf4j
@Service
public class BookService {

  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

//  /**
//   * Аннотация @PostConstruct позволяет произвести какие-то действия после создания бина
//   */
//  @PostConstruct
//  public void generateData() {
//    addBook("чистый код");
//    addBook("метрвые души");
//    addBook("война и мир");
//  }

  public List<Book> getAllBooks() {
    return BookJpaMapper.mapToBookList(bookRepository.findAll());
  }

  public Optional<Book> getBookById(Long id) {
    return Optional.of(BookJpaMapper.mapToBook(bookRepository.findById(id).orElseThrow()));
  }

  public List<Book> deleteBook(long id) {
    bookRepository.delete(bookRepository.findById(id).orElseThrow());
    return BookJpaMapper.mapToBookList(bookRepository.findAll());
  }

  public Book addBook(String name) {
    Book book = new Book(name);
    bookRepository.save(BookJpaMapper.mapToBookEntity(book));
    return book;
  }
}
