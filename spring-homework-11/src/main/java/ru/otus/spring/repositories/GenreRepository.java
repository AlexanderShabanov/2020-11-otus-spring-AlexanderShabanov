package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.models.Genre;

/**
 * @author Александр Шабанов
 */

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
