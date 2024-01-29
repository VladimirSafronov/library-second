package ru.safronov.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.safronov.library.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  Book findBookById(Long id);

}
