package ru.safronov.api;

import java.util.List;
import java.util.stream.Collectors;
import ru.safronov.core.domain.Reader;

/**
 * Mapper ReaderDTO <-> Reader
 */
public class ReaderMapper {

  public static ReaderDTO mapToReaderDto(Reader reader) {
    return new ReaderDTO(reader.getId(), reader.getName());
  }

  public static List<ReaderDTO> mapToReaderDtoList(List<Reader> readers) {
    return readers.stream()
        .map(ReaderMapper::mapToReaderDto)
        .collect(Collectors.toList());
  }
}
