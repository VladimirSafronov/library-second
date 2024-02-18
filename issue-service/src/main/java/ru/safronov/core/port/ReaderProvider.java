package ru.safronov.core.port;

import org.springframework.stereotype.Repository;
import ru.safronov.core.domain.Reader;

@Repository
public interface ReaderProvider {

  Reader findById(Long id);
  void save(Reader reader);

  void deleteAll();
}
