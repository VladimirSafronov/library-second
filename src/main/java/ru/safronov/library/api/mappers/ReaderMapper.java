package ru.safronov.library.api.mappers;

import java.util.List;
import java.util.stream.Collectors;
import ru.safronov.library.api.ReaderDTO;
import ru.safronov.library.model.Reader;

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
