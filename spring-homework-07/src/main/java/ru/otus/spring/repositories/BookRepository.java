package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
  @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
      attributePaths = {"author"})
  List<Book> findAll();
}
