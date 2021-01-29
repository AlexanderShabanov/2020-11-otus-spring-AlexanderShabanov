package ru.otus.spring.repositories;

import ru.otus.spring.models.Author;

import java.util.List;
import java.util.Optional;

/**
 * @author Александр Шабанов
 */
public interface AuthorRepository {
    List<Author> findAllAuthors();

    Optional<Author> findAuthorById(Long id);

    void insertAuthor(Author author);

    void updateAuthor(Author author);

    boolean checkAuthorExistsById(long id);
}
