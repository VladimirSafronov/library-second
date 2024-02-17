package ru.safronov.library.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.safronov.library.api.mappers.IssueMapper;
import ru.safronov.library.api.mappers.ReaderMapper;
import ru.safronov.library.service.ReaderService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/reader")
@Tag(name = "Reader")
public class ReaderController {

  private final ReaderService service;

  @GetMapping(path = "/{id}")
  @Operation(summary = "get reader by id", description = "Получение читателя по его идентификатору")
  public ResponseEntity<ReaderDTO> getReaderById(@PathVariable long id) {
    final ReaderDTO readerDTO;
    try {
      readerDTO = ReaderMapper.mapToReaderDto(service.getReader(id).orElseThrow());
    } catch (NoSuchElementException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return new ResponseEntity<>(readerDTO, HttpStatus.OK);
  }

  @PostMapping
  @Operation(summary = "create reader", description = "Создание нового читателя")
  public ResponseEntity<ReaderDTO> createReader(@RequestParam String name) {
    final ReaderDTO readerDTO = ReaderMapper.mapToReaderDto(service.createReader(name));
    return new ResponseEntity<>(readerDTO, HttpStatus.CREATED);
  }

  @DeleteMapping(path = "/{id}")
  @Operation(summary = "delete reader by id", description = "Удаление читателя по его идентификатору")
  public ResponseEntity<List<ReaderDTO>> deleteReader(@PathVariable long id) {
    final List<ReaderDTO> readers;
    try {
      readers = ReaderMapper.mapToReaderDtoList(service.deleteReader(id));
    } catch (NoSuchElementException ex) {
      return ResponseEntity.notFound().build();
    }
    return new ResponseEntity<>(readers, HttpStatus.OK);
  }

  @GetMapping(path = "/{id}/issue")
  @Operation(summary = "get reader issue list", description = "Получение списка записей выдачи книг читателя по его идентификатору")
  public ResponseEntity<List<IssueDTO>> getReaderIssueList(@PathVariable long id) {
    final List<IssueDTO> issueDTOList;
    try {
      issueDTOList = IssueMapper.mapToIssueDtoList(service.getIssueList(id).orElseThrow());
    } catch (NoSuchElementException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return new ResponseEntity<>(issueDTOList, HttpStatus.OK);
  }
}
