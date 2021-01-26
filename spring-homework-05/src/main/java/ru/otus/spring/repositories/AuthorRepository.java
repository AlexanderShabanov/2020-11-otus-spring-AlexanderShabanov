package ru.otus.spring.repositories;

import java.util.List;
import java.util.Optional;

import ru.otus.spring.models.Author;

/**
 * @author Александр Шабанов
 */
public interface AuthorRepository {
  List<Author> findAllAuthors();

  Optional<Author> findAuthorById(Long id);

  void insertAuthor(Author author);

  void updateAuthor(Author author);

}
