package ru.safronov.library.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.safronov.library.model.Issue;
import ru.safronov.library.model.Reader;
import ru.safronov.library.service.ReaderService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/reader")
@Tag(name = "Reader")
public class ReaderController {

  private final ReaderService service;

  @GetMapping(path = "/{id}")
  @Operation(summary = "get reader by id", description = "Получение читателя по его идентификатору")
  public ResponseEntity<Reader> getReaderById(@PathVariable long id) {
    return new ResponseEntity<>(service.getReader(id), HttpStatus.OK);
  }

  @PostMapping
  @Operation(summary = "create reader", description = "Создание нового читателя")
  public ResponseEntity<Reader> createReader(@RequestParam String name) {
    return new ResponseEntity<>(service.createReader(name), HttpStatus.CREATED);
  }

  @DeleteMapping(path = "/{id}")
  @Operation(summary = "delete reader by id", description = "Удаление читателя по его идентификатору")
  public ResponseEntity<List<Reader>> deleteReader(@PathVariable long id) {
    return new ResponseEntity<>(service.deleteReader(id), HttpStatus.OK);
  }

  @GetMapping(path = "/{id}/issue")
  @Operation(summary = "get reader issue list", description = "Получение списка записей выдачи книг читателя по его идентификатору")
  public ResponseEntity<List<Issue>> getReaderIssueList(@PathVariable long id) {
    return new ResponseEntity<>(service.getIssueList(id), HttpStatus.OK);
  }
}
