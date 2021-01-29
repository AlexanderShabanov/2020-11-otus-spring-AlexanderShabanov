package ru.otus.spring.repositories;

import ru.otus.spring.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAllBooks();

    Optional<Book> findBookById(long id);

    Long checkBookExistsById(long id);

    void insertBook(Book book);

    void updateBook(Book book);

    void deleteBookById(long id);
}
