package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.models.User;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
