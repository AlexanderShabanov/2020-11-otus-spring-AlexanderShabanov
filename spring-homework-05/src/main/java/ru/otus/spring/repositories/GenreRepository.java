package ru.otus.spring.repositories;

import java.util.List;
import java.util.Optional;

import ru.otus.spring.models.Genre;

/**
 * @author Александр Шабанов
 */
public interface GenreRepository {
  List<Genre> findAllGenres();

  Optional<Genre> findGenreById(long id);

  void insertGenre(Genre genre);

  void updateGenre(Genre genre);
}
