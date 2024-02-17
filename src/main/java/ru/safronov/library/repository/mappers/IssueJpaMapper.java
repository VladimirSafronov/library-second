package ru.safronov.library.repository.mappers;

import java.util.List;
import java.util.stream.Collectors;
import ru.safronov.library.model.Issue;
import ru.safronov.library.repository.IssueEntity;

public class IssueJpaMapper {

  public static IssueEntity mapToIssueEntity(Issue issue) {
    return new IssueEntity(issue.getId(), issue.getBookId(), issue.getReaderId());
  }

  public static Issue mapToIssue(IssueEntity entity) {
    return new Issue(entity.getId(), entity.getBookId(), entity.getReaderId(),
        entity.getIssued_at());
  }

  public static List<Issue> mapToIssueList(List<IssueEntity> entities) {
    return entities.stream()
        .map(IssueJpaMapper::mapToIssue)
        .collect(Collectors.toList());
  }
}
