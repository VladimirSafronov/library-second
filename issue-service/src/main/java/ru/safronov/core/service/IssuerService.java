package ru.safronov.core.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.safronov.core.domain.Book;
import ru.safronov.core.domain.Reader;
import ru.safronov.core.port.IssueStorage;
import ru.safronov.exceptions.TooManyBooksException;
import ru.safronov.poi.book.BookProviderImpl;
import ru.safronov.poi.reader.ReaderProviderImpl;
import ru.safronov.core.domain.Issue;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssuerService {

  /**
   * Максимально возможное количество книг на руках (1 если ничего не задано)
   */
  @Value("${application.issue.max-allowed-books:1}")
  private int maxAllowedBooks;
  private final BookProviderImpl bookProvider;
  private final ReaderProviderImpl readerProvider;
  private final IssueStorage issueStorage;

  public Issue issue(Issue request) {

    Issue issue;
    Book book = bookProvider.findById(request.getBookId());
    Reader reader = readerProvider.findById(request.getReaderId());

    if (reader.getBooksCount() < maxAllowedBooks) {
      issue = new Issue(book.getId(), reader.getId());
      issueStorage.save(issue);
      reader.setBooksCount(reader.getBooksCount() + 1);
      readerProvider.save(reader);
    } else {
      throw new TooManyBooksException(
          "У читателя с идетнификатором " + reader.getId() + " на руках "
              + "максимальное количество книг: " + reader.getBooksCount());
    }
    return issue;
  }

  public Issue getIssue(long id) {
    return issueStorage.findById(id);
  }

  public void returnBook(Issue issue) {

    Long readerId = issueStorage.findById(issue.getId()).getReaderId();
    Reader reader = readerProvider.findById(readerId);
    issue.setReturned_at(LocalDateTime.now());
    reader.setBooksCount(reader.getBooksCount() - 1);
    issueStorage.save(issue);

  }

  /**
   * Метод генерирует и возвращает список списков из (Название книги, Имя читателя, Дата заказа,
   * Дата возврата)
   */
  public List<List<String>> getIssuesData() {
    List<List<String>> allIssuesData = new ArrayList<>();
    for (Issue issue : issueStorage.findAll()) {
      String bookName = bookProvider.findById(issue.getBookId()).getName();
      String readerName = readerProvider.findById(issue.getReaderId()).getName();
      String issuedAt = issueStorage.findById(issue.getId()).getIssued_at().toString();
      String returnedAt =
          issueStorage.findById(issue.getId()).getReturned_at() == null ? "" :
              issueStorage.findById(issue.getId()).getReturned_at().toString();
      allIssuesData.add(List.of(bookName, readerName, issuedAt, returnedAt));
      log.info(allIssuesData.toString());
    }
    return allIssuesData;
  }
}
