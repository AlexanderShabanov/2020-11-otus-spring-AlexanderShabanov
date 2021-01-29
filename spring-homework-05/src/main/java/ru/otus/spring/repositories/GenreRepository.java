package ru.otus.spring.repositories;

import ru.otus.spring.models.Genre;

import java.util.List;
import java.util.Optional;

/**
 * @author Александр Шабанов
 */
public interface GenreRepository {
  List<Genre> findAllGenres();

  Optional<Genre> findGenreById(long id);

  void insertGenre(Genre genre);

  void updateGenre(Genre genre);

  boolean checkGenreExistsById(long id);
}
