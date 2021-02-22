package ru.otus.spring.service;

import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
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

    List<BookDto> findAllBooks();

    BookDto findBookById(long id);

    void insertBook(Book book, boolean createRelatedObjects);

    void updateBook(Book book, boolean createRelatedObjects);

    void deleteBook(long id);

    Long saveComment(Comment comment);

    Long saveComment(Long id, Long bookId, Long userId, String comment);

    List<CommentDto> findAllComments();

    List<CommentDto> findAllCommentsByBookId(Long bookId);

}
