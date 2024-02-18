package ru.safronov.api;

import java.util.List;
import java.util.stream.Collectors;
import ru.safronov.core.domain.Issue;

/**
 * Mapper IssueDTO <-> Issue
 */
public class IssueMapper {

  public static IssueDTO mapToIssueDto(Issue issue) {
    return new IssueDTO(issue.getId(), issue.getBookId(), issue.getReaderId(), issue.getIssued_at());
  }

  public static List<IssueDTO> mapToIssueDtoList(List<Issue> issues) {
    return issues.stream()
        .map(IssueMapper::mapToIssueDto)
        .collect(Collectors.toList());
  }
}
