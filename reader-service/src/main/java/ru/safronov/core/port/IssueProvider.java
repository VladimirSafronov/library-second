package ru.safronov.core.port;

import java.util.List;
import ru.safronov.core.domain.Issue;

public interface IssueProvider {

  List<Issue> findAllById(Long readerId);
}
