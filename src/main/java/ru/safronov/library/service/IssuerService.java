package ru.safronov.library.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.safronov.library.api.IssueRequest;
import ru.safronov.library.exceptions.TooManyBooksException;
import ru.safronov.library.model.Book;
import ru.safronov.library.model.Issue;
import ru.safronov.library.model.Reader;
import ru.safronov.library.repository.BookRepository;
import ru.safronov.library.repository.IssueRepository;
import ru.safronov.library.repository.ReaderRepository;

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

  public Issue issue(IssueRequest request) {

    Issue issue;
    Book book = bookRepository.getReferenceById(request.getBookId());
    Reader reader = readerRepository.getReferenceById(request.getReaderId());
    if (reader.getBooksCount() < maxAllowedBooks) {
      issue = new Issue(book.getId(), reader.getId());
      issueRepository.save(issue);
      reader.setBooksCount(reader.getBooksCount() + 1);
      readerRepository.saveAndFlush(reader);
    } else {
      throw new TooManyBooksException(
          "У читателя с идетнификатором " + reader.getId() + " на руках "
              + "максимальное количество книг: " + reader.getBooksCount());
    }
    return issue;
  }

  public Issue getIssue(long id) {
    return issueRepository.getReferenceById(id);
  }

  public void returnBook(Issue issue) {

    Reader reader = readerRepository.getReferenceById(
        issueRepository.getReferenceById(issue.getId()).getReaderId());
    issue.setReturned_at(LocalDateTime.now());
    reader.setBooksCount(reader.getBooksCount() - 1);
    issueRepository.saveAndFlush(issue);

  }

  /**
   * Метод генерирует и возвращает список списков из (Название книги, Имя читателя, Дата заказа,
   * Дата возврата)
   */
  public List<List<String>> getIssuesData() {
    List<List<String>> allIssuesData = new ArrayList<>();
    for (Issue issue : issueRepository.findAll()) {
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
