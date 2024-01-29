package ru.safronov.library.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.safronov.library.model.Book;
import ru.safronov.library.repository.BookRepository;

@Service
public class BookService {

  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  /**
   * Аннотация @PostConstruct позволяет произвести какие-то действия после создания бина
   */
  @PostConstruct
  public void generateData() {
    addBook("чистый код");
    addBook("метрвые души");
    addBook("война и мир");
  }
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public Book getBookById(Long id) {
    return bookRepository.getReferenceById(id);
  }

  public List<Book> deleteBook(long id) {
    bookRepository.delete(bookRepository.getReferenceById(id));
    return bookRepository.findAll();
  }

  public Book addBook(String name) {
    return bookRepository.save(new Book(name));
  }
}
