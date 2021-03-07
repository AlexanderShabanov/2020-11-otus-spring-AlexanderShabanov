package ru.otus.spring.service;

import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryService {
    List<AuthorDto> findAllAuthors();

    Optional<AuthorDto> findAuthorById(String id);

    void insertAuthor(AuthorDto author);

    void updateAuthor(Author author);

    List<GenreDto> findAllGenres();

    Optional<GenreDto> findGenreById(String id);

    void insertGenre(Genre genre);

    void updateGenre(Genre genre);

    List<BookDto> findAllBooks();

    BookDto findBookById(String id);

    BookDto insertBook(BookDto book, boolean createRelatedObjects);

    BookDto updateBook(BookDto book, boolean createRelatedObjects);

    void deleteBook(String id);

    String saveComment(Comment comment);

    String saveComment(String id, String bookId, String userId, String comment);

    List<CommentDto> findAllComments();

    List<CommentDto> findAllCommentsByBookId(String bookId);

}
