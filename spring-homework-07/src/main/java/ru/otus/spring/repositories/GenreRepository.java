package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Genre;

/**
 * @author Александр Шабанов
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
