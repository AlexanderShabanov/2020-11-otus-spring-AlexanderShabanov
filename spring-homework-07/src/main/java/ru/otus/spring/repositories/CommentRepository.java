package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
     @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
         attributePaths = {"user"})
     List<Comment> findByBookId(Long id);
}
