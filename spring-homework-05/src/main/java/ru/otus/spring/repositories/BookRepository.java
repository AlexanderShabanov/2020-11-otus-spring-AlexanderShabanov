package ru.otus.spring.repositories;

import java.util.List;
import java.util.Optional;

import ru.otus.spring.models.Book;

public interface BookRepository {
    List<Book> findAllBooks();

    Optional<Book> findBookById(long id);

    void insertBook(Book book);

    void updateBook(Book book);
    void deleteBookById(long id);
}
