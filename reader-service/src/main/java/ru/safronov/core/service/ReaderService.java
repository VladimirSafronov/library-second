package ru.safronov.core.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.safronov.core.domain.Issue;
import ru.safronov.poi.book.BookProviderImpl;
import ru.safronov.poi.issue.IssueProviderImpl;
import ru.safronov.core.domain.Reader;
import ru.safronov.repository.ReaderRepository;
import ru.safronov.repository.ReaderJpaMapper;

@Service
@RequiredArgsConstructor
public class ReaderService {

  private final ReaderRepository readerRepository;
  private final BookProviderImpl bookProvider;
  private final IssueProviderImpl issueProvider;

  /**
   * Аннотация @PostConstruct позволяет произвести какие-то действия после создания бина
   */
  @PostConstruct
  public void generateData() {
    createReader("Игорь");
    createReader("Федор");
    createReader("Ольга");
  }

  public Optional<Reader> getReader(long id) {
    return Optional.of(ReaderJpaMapper.mapToReader(readerRepository.findById(id).orElseThrow()));
  }

  public Reader createReader(String name) {
    Reader reader = new Reader(name);
    readerRepository.save(ReaderJpaMapper.mapToReaderEntity(reader));
    return reader;
  }

  public List<Reader> deleteReader(long id) {
    readerRepository.delete(readerRepository.findById(id).orElseThrow());
    return ReaderJpaMapper.mapToReaderList(readerRepository.findAll());
  }

  public Optional<List<Issue>> getIssueList(long id) {
    return Optional.of(issueProvider.findAllById(id));
  }

  /**
   * Метод создает и возвращает список книг, которые у читателя на руках
   */
  public List<String> getReaderBooks(Reader reader) {
    List<String> books = new ArrayList<>();
    for (Issue issue : getIssueList(reader.getId()).orElseThrow()) {
      if (issue.getReturned_at() == null) {
        books.add(bookProvider.findById(issue.getBookId()).getName());
      }
    }
    return books;
  }

  public void deleteAllReaders() {
    readerRepository.deleteAll();
  }

  public List<Reader> getAllReaders() {
    return ReaderJpaMapper.mapToReaderList(readerRepository.findAll());
  }
}
