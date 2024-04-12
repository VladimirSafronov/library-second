package ru.safronov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.safronov.core.domain.Issue;
import ru.safronov.poi.issue.IssueDto;
import ru.safronov.poi.issue.IssueMapper;

public class IssueMapperTests {

  IssueDto testIssueDto;
  Issue testIssue;
  private final Long testId = 105L;
  private final Long bookId = 95L;
  private final Long readerId = 10L;
  private final LocalDateTime issued_at = LocalDateTime.MIN;
  private final LocalDateTime returned_at = LocalDateTime.MAX;
  private final int listCount = 4;

  @BeforeEach
  void prepareTestData() {
    testIssueDto = new IssueDto(testId, bookId, readerId, issued_at, returned_at);
    testIssue = new Issue(testId, bookId, readerId, issued_at, returned_at);
  }

  @AfterEach
  void cleanTestData() {
    testIssue = null;
    testIssueDto = null;
  }

  @Test
  void mapToIssueDtoThenSuccess() {
    IssueDto result = IssueMapper.mapToIssueDto(testIssue);
    assertNotNull(result);
    assertEquals(testId, result.getId());
    assertEquals(bookId, result.getBookId());
    assertEquals(readerId, result.getReaderId());
    assertEquals(issued_at, result.getIssued_at());
    assertEquals(returned_at, result.getReturned_at());
  }

  @Test
  void mapToIssueDtoWithDiffDataThenError() {
    testIssue.setId(testIssue.getId() + 1);
    testIssue.setBookId(testIssue.getBookId() - 1);
    testIssue.setReaderId(testIssue.getReaderId() + 1);
    testIssue.setIssued_at(LocalDateTime.of(2000, 1, 1, 1, 1));
    testIssue.setReturned_at(LocalDateTime.of(2010, 1, 1, 1, 1));
    IssueDto result = IssueMapper.mapToIssueDto(testIssue);
    assertNotNull(result);
    assertNotEquals(testId, result.getId());
    assertNotEquals(bookId, result.getBookId());
    assertNotEquals(readerId, result.getReaderId());
    assertNotEquals(issued_at, result.getIssued_at());
    assertNotEquals(returned_at, result.getReturned_at());
  }

  @Test
  void mapToIssueThenSuccess() {
    Issue result = IssueMapper.mapToIssue(testIssueDto);
    assertNotNull(result);
    assertEquals(testIssueDto.getId(), result.getId());
    assertEquals(testIssueDto.getBookId(), result.getBookId());
    assertEquals(testIssueDto.getReaderId(), result.getReaderId());
    assertEquals(testIssueDto.getIssued_at(), result.getIssued_at());
    assertEquals(testIssueDto.getReturned_at(), result.getReturned_at());
  }

  @Test
  void mapToIssueWithDiffDataThenError() {
    testIssueDto.setId(testIssue.getId() - 1);
    testIssueDto.setBookId(testIssueDto.getBookId() + 1);
    testIssueDto.setReaderId(testIssueDto.getReaderId() - 1);
    testIssueDto.setIssued_at(LocalDateTime.of(2001, 2, 2, 2, 2));
    testIssueDto.setReturned_at(LocalDateTime.of(2009, 3, 3, 3, 3));
    Issue result = IssueMapper.mapToIssue(testIssueDto);
    assertNotNull(result);
    assertNotEquals(testId, result.getId());
    assertNotEquals(bookId, result.getBookId());
    assertNotEquals(readerId, result.getReaderId());
    assertNotEquals(issued_at, result.getIssued_at());
    assertNotEquals(returned_at, result.getReturned_at());
  }

  @Test
  void mapToIssueListThenAllEqual() {
    List<IssueDto> issueDtos = getListIssueDtos();
    List<Issue> result = IssueMapper.mapToIssueList(issueDtos);
    assertNotNull(result);
    assertEquals(issueDtos.size(), result.size());
    for (int i = 0; i < listCount; i++) {
      assertEquals(issueDtos.get(i).getId(), result.get(i).getId());
      assertEquals(issueDtos.get(i).getBookId(), result.get(i).getBookId());
      assertEquals(issueDtos.get(i).getReaderId(), result.get(i).getReaderId());
      assertEquals(issueDtos.get(i).getIssued_at(), result.get(i).getIssued_at());
      assertEquals(issueDtos.get(i).getReturned_at(), result.get(i).getReturned_at());
    }
  }

  @Test
  void mapToIssueDtoListThenAllEqual() {
    List<Issue> issues = getListIssues();
    List<IssueDto> result = IssueMapper.mapToIssueDtoList(issues);
    assertNotNull(result);
    assertEquals(issues.size(), result.size());
    for (int i = 0; i < listCount; i++) {
      assertEquals(issues.get(i).getId(), result.get(i).getId());
      assertEquals(issues.get(i).getBookId(), result.get(i).getBookId());
      assertEquals(issues.get(i).getReaderId(), result.get(i).getReaderId());
      assertEquals(issues.get(i).getIssued_at(), result.get(i).getIssued_at());
      assertEquals(issues.get(i).getReturned_at(), result.get(i).getReturned_at());
    }
  }

  private List<Issue> getListIssues() {
    List<Issue> issues = new ArrayList<>();
    for (int i = 1; i <= listCount; i++) {
      issues.add(new Issue(testId + i, bookId + i, readerId + 1, issued_at, returned_at));
    }
    return issues;
  }

  private List<IssueDto> getListIssueDtos() {
    List<IssueDto> issueDtos = new ArrayList<>();
    for (int i = 1; i <= listCount; i++) {
      issueDtos.add(new IssueDto(testId - i, bookId - i, readerId - 1, issued_at, returned_at));
    }
    return issueDtos;
  }
}
