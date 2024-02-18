package ru.safronov.poi.reader;

import ru.safronov.core.domain.Reader;

public class ReaderMapper {

  static Reader mapToReader(ReaderDto readerDto) {
    return new Reader(readerDto.getId(), readerDto.getName(), readerDto.getBooksCount());
  }

  static ReaderDto mapToReaderDto(Reader reader) {
    return new ReaderDto(reader.getId(), reader.getName(), reader.getBooksCount());
  }
}
