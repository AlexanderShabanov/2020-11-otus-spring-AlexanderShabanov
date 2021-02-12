package ru.otus.spring.repositories;

import ru.otus.spring.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    List<Comment> findAll();

    List<Comment> findAllByBookId(Long bookId);

    Optional<Comment> findById(Long id);

    long save(Comment comment);

}
