package ru.safronov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.safronov.core.domain.Reader;
import ru.safronov.repository.ReaderEntity;
import ru.safronov.repository.ReaderJpaMapper;

public class ReaderJpaMapperTests {

  Reader testReader;
  ReaderEntity testReaderEntity;
  private final long readerId = 10L;
  private final String readerName = "test reader";
  private final int readerBookCount = 3;
  private final int readersListCount = 5;

  @BeforeEach
  void prepareTestData() {
    testReader = new Reader(readerId, readerName, readerBookCount);
    testReaderEntity = new ReaderEntity(readerId, readerName, readerBookCount);
  }

  @AfterEach
  void cleanTestData() {
    testReader = null;
    testReaderEntity = null;
  }

  @Test
  void mapToReaderThenSuccess() {
    Reader result = ReaderJpaMapper.mapToReader(testReaderEntity);
    assertNotNull(result);
    assertEquals(testReaderEntity.getId(), result.getId());
    assertEquals(testReaderEntity.getName(), result.getName());
    assertEquals(testReaderEntity.getBooksCount(), result.getBooksCount());
  }

  @Test
  void mapToReaderDiffDataThenError() {
    testReaderEntity.setId(testReaderEntity.getId() + 1);
    testReaderEntity.setName(testReaderEntity.getName() + "a");
    testReaderEntity.setBooksCount(testReaderEntity.getBooksCount() - 1);
    Reader result = ReaderJpaMapper.mapToReader(testReaderEntity);
    assertNotNull(result);
    assertNotEquals(readerId, result.getId());
    assertNotEquals(readerName, result.getName());
    assertNotEquals(readerBookCount, result.getBooksCount());
  }

  @Test
  void mapToReaderEntityThenAccess() {
    ReaderEntity result = ReaderJpaMapper.mapToReaderEntity(testReader);
    assertNotNull(result);
    assertEquals(testReader.getId(), result.getId());
    assertEquals(testReader.getName(), result.getName());
    assertEquals(testReader.getBooksCount(), result.getBooksCount());
  }

  @Test
  void mapToReaderEntityWithDiffDataThenError() {
    testReader.setId(testReader.getId() + 1);
    testReader.setName(testReader.getName() + "!");
    testReader.setBooksCount(testReader.getBooksCount() - 1);
    ReaderEntity result = ReaderJpaMapper.mapToReaderEntity(testReader);
    assertNotNull(result);
    assertNotEquals(readerId, result.getId());
    assertNotEquals(readerName, result.getName());
    assertNotEquals(readerBookCount, result.getBooksCount());
  }

  @Test
  void mapToReaderListThenAllDataEquals() {
    List<ReaderEntity> readerEntities = getReaderEntityList();
    List<Reader> result = ReaderJpaMapper.mapToReaderList(readerEntities);
    assertNotNull(result);
    assertEquals(readerEntities.size(), result.size());
    for (int i = 0; i < readersListCount; i++) {
      assertEquals(readerEntities.get(i).getId(), result.get(i).getId());
      assertEquals(readerEntities.get(i).getName(), result.get(i).getName());
      assertEquals(readerEntities.get(i).getBooksCount(), result.get(i).getBooksCount());
    }
  }

  private List<ReaderEntity> getReaderEntityList() {
    List<ReaderEntity> result = new ArrayList<>();
    for (int i = 1; i <= readersListCount; i++) {
      result.add(new ReaderEntity(readerId + i, readerName + " " + i, readerBookCount + i));
    }
    return result;
  }
}
