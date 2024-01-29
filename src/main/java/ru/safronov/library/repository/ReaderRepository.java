package ru.safronov.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.safronov.library.model.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

}
