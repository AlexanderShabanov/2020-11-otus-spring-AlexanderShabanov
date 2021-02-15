package ru.otus.spring.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.LibraryService;

import java.util.List;
import java.util.Optional;

/**
 * @author Александр Шабанов
 */
@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {
    private final LibraryService libraryService;
    @ShellMethod(key = {"af", "authorFindAll"}, value = "get all authors")
    public List<Author> authorFindAll() {
        return libraryService.findAllAuthors();
    }

    @ShellMethod(key = {"afid", "authorFindById"}, value = "get author by id")
    public Optional<Author> authorFindById(long id) {
        return libraryService.findAuthorById(id);
    }

    @ShellMethod(key = {"ai", "authorInsert"}, value = "insert author")
    public void authorInsert(long id, String name, String surName) {
        libraryService.insertAuthor(new Author(id, name, surName));
    }

    @ShellMethod(key = {"au", "authorUpdate"}, value = "update author")
    public void authorUpdate(long id, String name, String surName) {
        libraryService.updateAuthor(new Author(id, name, surName));
    }

    @ShellMethod(key = {"gf", "genreFindAll"}, value = "get all genres")
    public List<Genre> genreFindAll() {
        return libraryService.findAllGenres();
    }

    @ShellMethod(key = {"gfid", "genreFindById"}, value = "get genre by id")
    public Optional<Genre> genreFindById(long id) {
        return libraryService.findGenreById(id);
    }

    @ShellMethod(key = {"gi", "genreInsert"}, value = "insert genre")
    public void genreInsert(long id, String name) {
        libraryService.insertGenre(new Genre(id, name));
    }

    @ShellMethod(key = {"gu", "genreUpdate"}, value = "update genre")
    public void genreUpdate(long id, String name) {
        libraryService.updateGenre(new Genre(id, name));
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = {"bf", "bookFindAll"}, value = "get all books")
    public void bookFindAll() {
        libraryService.findAllBooks().forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = {"bfid", "bookFindById"}, value = "get book by id")
    public void bookFindById(long id) {
        System.out.println(libraryService.findBookById(id));
    }

    @ShellMethod(key = {"bi_id", "bookInsert_id"}, value = "insert book with existed author and genre")
    public void bookInsert(long authorId, long genreId, String name) {
        libraryService.insertBook(new Book(null, new Author(authorId, "", ""), List.of(new Genre(genreId, "")), name), false);
    }

    @ShellMethod(key = {"bi_full", "bookInsert_full"}, value = "insert book_full")
    public void bookInsert(long authorId, String authorName, String authorSurName, long genreId, String genreName, String name) {
        libraryService.insertBook(new Book(null, new Author(authorId, authorName, authorSurName), List.of(new Genre(genreId, genreName)), name), true);
    }

    @ShellMethod(key = {"bu_full", "bookUpdate_full"}, value = "update book_full")
    public void bookUpdate(long id, long authorId, String authorName, String authorSurName, long genreId, String genreName, String name) {
        libraryService.updateBook(new Book(id, new Author(authorId, authorName, authorSurName), List.of(new Genre(genreId, genreName)), name), true);
    }

    @ShellMethod(key = {"bu_id", "bookUpdate_id"}, value = "update book with existed author and genre")
    public void bookUpdate(long id, long authorId, long genreId, String name) {
        libraryService.updateBook(new Book(id, new Author(authorId, "", ""), List.of(new Genre(genreId, "")), name), false);
    }

    @ShellMethod(key = {"bd_id", "bookDelete_id"}, value = "delete book ny id")
    public void bookDelete(long id) {
        libraryService.deleteBook(id);
    }

    @ShellMethod(key = {"cf_bid", "commentsFind_bookId"}, value = "findAllCommentsByBookId")
    @Transactional(readOnly = true)
    public void commentsFindByBookId(Long bookId) {
        libraryService.findAllCommentsByBookId(bookId).forEach(System.out::println);
    }

    @ShellMethod(key = {"cf", "commentsFindAll"}, value = "findAllComments")
    @Transactional(readOnly = true)
    public void commentsFindAll() {
        libraryService.findAllComments().forEach(System.out::println);
    }

    @ShellMethod(key = {"cs", "commentSave"}, value = "saveComment")
    public Long commentSave(Long bookId, Long userId, String comment) {
        return libraryService.saveComment(null, bookId, userId, comment);
    }

}
