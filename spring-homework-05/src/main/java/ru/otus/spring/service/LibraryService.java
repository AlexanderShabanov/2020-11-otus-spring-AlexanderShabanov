package ru.otus.spring.service;

import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryService {
    List<Author> findAllAuthors();

    Optional<Author> findAuthorById(Long id);

    void insertAuthor(Author author);

    void updateAuthor(Author author);

    List<Genre> findAllGenres();

    Optional<Genre> findGenreById(long id);

    void insertGenre(Genre genre);

    void updateGenre(Genre genre);

    List<Book> findAllBooks();

    Optional<Book> findBookById(long id);

    void insertBook(Book book, boolean createRelatedObjects);

    void updateBook(Book book, boolean createRelatedObjects);

    void deleteBook(long id);

}
