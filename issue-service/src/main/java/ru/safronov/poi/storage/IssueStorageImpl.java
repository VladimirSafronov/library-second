package ru.safronov.poi.storage;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.safronov.core.domain.Issue;
import ru.safronov.core.port.IssueStorage;

@Service
@RequiredArgsConstructor
public class IssueStorageImpl implements IssueStorage {

  private final IssueRepository issueRepository;

  @Override
  public void save(Issue issue) {
    issueRepository.saveAndFlush(IssueJpaMapper.mapToIssueEntity(issue));
  }

  @Override
  public Issue findById(Long id) {
    IssueEntity issueEntity = issueRepository.findById(id).orElseThrow();
    return IssueJpaMapper.mapToIssue(issueEntity);
  }

  @Override
  public List<Issue> findAll() {
    List<IssueEntity> allIssues = issueRepository.findAll();
    return IssueJpaMapper.mapToIssueList(allIssues);
  }

  @Override
  public void deleteAll() {
    issueRepository.deleteAll();
  }

}
