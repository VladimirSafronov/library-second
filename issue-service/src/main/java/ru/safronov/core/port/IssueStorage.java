package ru.safronov.core.port;

import java.util.List;
import org.springframework.stereotype.Repository;
import ru.safronov.core.domain.Issue;

@Repository
public interface IssueStorage {

  void save(Issue issue);
  Issue findById(Long id);
  List<Issue> findAll();

  void deleteAll();
}
