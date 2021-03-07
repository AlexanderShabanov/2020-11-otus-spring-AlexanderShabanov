package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.User;

public interface UserRepository extends MongoRepository<User, String> {
}
