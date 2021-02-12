package ru.otus.spring.repositories;

import ru.otus.spring.models.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Long save(User user);
}
