package ru.safronov.library.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.safronov.library.exceptions.TooManyBooksException;
import ru.safronov.library.model.Book;
import ru.safronov.library.model.Issue;
import ru.safronov.library.model.Reader;
import ru.safronov.library.repository.BookRepository;
import ru.safronov.library.repository.IssueRepository;
import ru.safronov.library.repository.ReaderRepository;
import ru.safronov.library.repository.mappers.BookJpaMapper;
import ru.safronov.library.repository.mappers.IssueJpaMapper;
import ru.safronov.library.repository.mappers.ReaderJpaMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssuerService {

  /**
   * Максимально возможное количество книг на руках (1 если ничего не задано)
   */
  @Value("${application.issue.max-allowed-books:1}")
  private int maxAllowedBooks;
  private final BookRepository bookRepository;
  private final ReaderRepository readerRepository;
  private final IssueRepository issueRepository;

  public Issue issue(Issue request) {

    Issue issue;
    Book book = BookJpaMapper.mapToBook(bookRepository.findById(request.getBookId()).orElseThrow());
    Reader reader = ReaderJpaMapper.mapToReader(
        readerRepository.findById(request.getReaderId()).orElseThrow());
    if (reader.getBooksCount() < maxAllowedBooks) {
      issue = new Issue(book.getId(), reader.getId());
      issueRepository.save(IssueJpaMapper.mapToIssueEntity(issue));
      reader.setBooksCount(reader.getBooksCount() + 1);
      readerRepository.saveAndFlush(ReaderJpaMapper.mapToReaderEntity(reader));
    } else {
      throw new TooManyBooksException(
          "У читателя с идетнификатором " + reader.getId() + " на руках "
              + "максимальное количество книг: " + reader.getBooksCount());
    }
    return issue;
  }

  public Optional<Issue> getIssue(long id) {
    return Optional.of(IssueJpaMapper.mapToIssue(issueRepository.findById(id).orElseThrow()));
  }

  public void returnBook(Issue issue) {

    Long readerId = issueRepository.findById(issue.getId()).orElseThrow().getReaderId();
    Optional<Reader> reader = Optional.of(
        ReaderJpaMapper.mapToReader(readerRepository.findById(readerId).orElseThrow()));
    issue.setReturned_at(LocalDateTime.now());
    Reader gotReader = reader.get();
    gotReader.setBooksCount(gotReader.getBooksCount() - 1);
    issueRepository.saveAndFlush(IssueJpaMapper.mapToIssueEntity(issue));

  }

  /**
   * Метод генерирует и возвращает список списков из (Название книги, Имя читателя, Дата заказа,
   * Дата возврата)
   */
  public List<List<String>> getIssuesData() {
    List<List<String>> allIssuesData = new ArrayList<>();
    for (Issue issue : IssueJpaMapper.mapToIssueList(issueRepository.findAll())) {
      String bookName = bookRepository.getReferenceById(issue.getBookId()).getName();
      String readerName = readerRepository.getReferenceById(issue.getReaderId()).getName();
      String issuedAt = issueRepository.getReferenceById(issue.getId()).getIssued_at().toString();
      String returnedAt =
          issueRepository.getReferenceById(issue.getId()).getReturned_at() == null ? "" :
              issueRepository.getReferenceById(issue.getId()).getReturned_at().toString();
      allIssuesData.add(List.of(bookName, readerName, issuedAt, returnedAt));
      log.info(allIssuesData.toString());
    }
    return allIssuesData;
  }
}
