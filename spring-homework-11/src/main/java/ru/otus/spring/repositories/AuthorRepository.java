package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.models.Author;

/**
 * @author Александр Шабанов
 */

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
