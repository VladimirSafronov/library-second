package ru.safronov.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.safronov.library.model.Reader;
import ru.safronov.library.service.BookService;
import ru.safronov.library.service.IssuerService;
import ru.safronov.library.service.ReaderService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ui")
public class HtmlController {

  private final BookService bookService;
  private final ReaderService readerService;
  private final IssuerService issuerService;

  @GetMapping("/books")
  public String getAvailableBooks(Model model) {
    model.addAttribute("books", bookService.getAllBooks());
    return "availableBooks";
  }

  @GetMapping("/readers")
  public String getReaders(Model model) {
    model.addAttribute("readers", readerService.getAllReaders());
    return "readers";
  }

  @GetMapping("/issues")
  public String getIssues(Model model) {

    model.addAttribute("issues", issuerService.getIssuesData());
    return "issues";
  }

  @GetMapping("/reader/{id}")
  public String getReaderInfo(@PathVariable Long id, Model model) {
    Reader reader = readerService.getReader(id);
    model.addAttribute("reader", reader);
    model.addAttribute("readerBooks", readerService.getReaderBooks(reader));
    return "readerInfo";
  }
}
