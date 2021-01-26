package ru.otus.spring.service;

import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryService {
    public List<Author> findAllAuthors();
    public Optional<Author> findAuthorById(Long id);
    public void insertAuthor(Author author);
    public void updateAuthor(Author author);

    public List<Genre> findAllGenres();
    public Optional<Genre> findGenreById(long id);
    public void insertGenre(Genre genre);
    public void updateGenre(Genre genre);

    public List<Book> findAllBooks();
    public Optional<Book> findBookById(long id);
    public void insertBook(Book book, boolean createRelatedObjects);
    public void updateBook(Book book, boolean createRelatedObjects);
    public void deleteBook(long id);

}
