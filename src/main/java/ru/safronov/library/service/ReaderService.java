package ru.safronov.library.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.safronov.library.model.Issue;
import ru.safronov.library.model.Reader;
import ru.safronov.library.repository.BookRepository;
import ru.safronov.library.repository.IssueRepository;
import ru.safronov.library.repository.ReaderRepository;

@Service
@RequiredArgsConstructor
public class ReaderService {

  private final ReaderRepository readerRepository;
  private final BookRepository bookRepository;
  private final IssueRepository issueRepository;

  public Reader getReader(long id) {
    return readerRepository.getReferenceById(id);
  }

  /**
   * Аннотация @PostConstruct позволяет произвести какие-то действия после создания бина
   */
  @PostConstruct
  public void generateData() {
    createReader("Игорь");
    createReader("Федор");
    createReader("Ольга");
  }

  public Reader createReader(String name) {
    Reader reader = new Reader(name);
    readerRepository.save(reader);
    return reader;
  }

  public List<Reader> deleteReader(long id) {
    Reader reader = readerRepository.getReferenceById(id);
    readerRepository.delete(reader);
    return readerRepository.findAll();
  }

  public List<Issue> getIssueList(long id) {
    return issueRepository.findAllByReaderId(id);
  }

  /**
   * Метод создает и возвращает список книг, которые у читателя на руках
   */
  public List<String> getReaderBooks(Reader reader) {
    List<String> books = new ArrayList<>();
    for (Issue issue : getIssueList(reader.getId())) {
      if (issue.getReturned_at() == null) {
        books.add(bookRepository.findBookById(issue.getBookId()).getName());
      }
    }
    return books;
  }

  public List<Reader> getAllReaders() {
    return readerRepository.findAll();
  }
}
