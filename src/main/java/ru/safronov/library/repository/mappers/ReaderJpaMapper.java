package ru.safronov.library.repository.mappers;

import java.util.List;
import java.util.stream.Collectors;
import ru.safronov.library.model.Reader;
import ru.safronov.library.repository.ReaderEntity;

public class ReaderJpaMapper {

  public static Reader mapToReader(ReaderEntity entity) {
    return new Reader(entity.getId(), entity.getName());
  }

  public static ReaderEntity mapToReaderEntity(Reader reader) {
    return new ReaderEntity(reader.getId(), reader.getName(), reader.getBooksCount());
  }

  public static List<Reader> mapToReaderList(List<ReaderEntity> readers) {
    return readers.stream()
        .map(ReaderJpaMapper::mapToReader)
        .collect(Collectors.toList());
  }
}
