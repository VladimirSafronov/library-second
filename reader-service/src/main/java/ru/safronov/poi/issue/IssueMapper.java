package ru.safronov.poi.issue;

import java.util.List;
import java.util.stream.Collectors;
import ru.safronov.core.domain.Issue;

public class IssueMapper {

  public static Issue mapToIssue(IssueDto issueDto) {
    Issue issue = new Issue(issueDto.getId(), issueDto.getBookId(), issueDto.getReaderId(),
        issueDto.getIssued_at());
    if (issueDto.getReturned_at() != null) {
      issue.setReturned_at(issueDto.getReturned_at());
    }
    return issue;
  }

  public static IssueDto mapToIssueDto(Issue issue) {
    IssueDto issueDto = new IssueDto(issue.getId(), issue.getBookId(), issue.getReaderId(),
        issue.getIssued_at());
    if (issue.getReturned_at() != null) {
      issueDto.setReturned_at(issue.getReturned_at());
    }
    return issueDto;
  }

  public static List<Issue> mapToIssueList(List<IssueDto> list) {
    return list.stream()
        .map(IssueMapper::mapToIssue)
        .collect(Collectors.toList());
  }

  public static List<IssueDto> mapToIssueDtoList(List<Issue> list) {
    return list.stream()
        .map(IssueMapper::mapToIssueDto)
        .collect(Collectors.toList());
  }
}
