package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.Author;

/**
 * @author Александр Шабанов
 */

public interface AuthorRepository extends MongoRepository<Author, String> {
}
