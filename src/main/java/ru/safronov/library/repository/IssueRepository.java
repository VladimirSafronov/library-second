package ru.safronov.library.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.safronov.library.model.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

  List<Issue> findAllByReaderId(Long id);
}
