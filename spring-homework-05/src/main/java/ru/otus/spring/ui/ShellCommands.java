package ru.otus.spring.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
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
        var listAuthor = libraryService.findAllAuthors();
        return listAuthor;
    }

    @ShellMethod(key = {"afid", "authorFindById"}, value = "get author by id")
    public Optional<Author> authorFindById(long id) {
        Optional<Author> author = libraryService.findAuthorById(id);
        //System.out.println(author.orElseThrow(()->new RuntimeException("нема авторов")));
        return author;
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
        var listGenre = libraryService.findAllGenres();
        return listGenre;
    }

    @ShellMethod(key = {"gfid", "genreFindById"}, value = "get genre by id")
    public Optional<Genre> genreFindById(long id) {
        Optional<Genre> genre = libraryService.findGenreById(id);
        return genre;
    }

    @ShellMethod(key = {"gi", "genreInsert"}, value = "insert genre")
    public void genreInsert(long id, String name) {
        libraryService.insertGenre(new Genre(id, name));
    }

    @ShellMethod(key = {"gu", "genreUpdate"}, value = "update genre")
    public void genreUpdate(long id, String name) {
        libraryService.updateGenre(new Genre(id, name));
    }

    @ShellMethod(key = {"bf", "bookFindAll"}, value = "get all books")
    public List<Book> bookFindAll() {
        var listBook = libraryService.findAllBooks();
        return listBook;
    }

    @ShellMethod(key = {"bfid", "bookFindById"}, value = "get book by id")
    public Optional<Book> bookFindById(long id) {
        Optional<Book> book = libraryService.findBookById(id);
        return book;
    }

    @ShellMethod(key = {"bi_id", "bookInsert_id"}, value = "insert book with existed author and genre")
    public void bookInsert(long id, long author_id, long genre_id,  String name) {
        libraryService.insertBook(new Book(id, new Author(author_id, "", ""), new Genre(genre_id, ""), name),false);
    }

    @ShellMethod(key = {"bi_full", "bookInsert_full"}, value = "insert book_full")
    public void bookInsert(long id, long author_id, String author_name, String author_surName, long genre_id, String genre_name, String name) {
        libraryService.insertBook(new Book(id, new Author(author_id, author_name, author_surName), new Genre(genre_id, genre_name), name),true);
    }

    @ShellMethod(key = {"bu_full", "bookUpdate_full"}, value = "update book_full")
    public void bookUpdate(long id, long author_id, String author_name, String author_surName, long genre_id, String genre_name, String name) {
        libraryService.updateBook(new Book(id, new Author(author_id, author_name, author_surName), new Genre(genre_id, genre_name), name), true);
    }

    @ShellMethod(key = {"bu_id", "bookUpdate_id"}, value = "update book with existed author and genre")
    public void bookUpdate(long id, long author_id, long genre_id,  String name) {
        libraryService.updateBook(new Book(id, new Author(author_id, "", ""), new Genre(genre_id, ""), name),false);
    }

    @ShellMethod(key = {"bd_id", "bookDelete_id"}, value = "delete book ny id")
    public void bookDelete(long id) {
        libraryService.deleteBook(id);
    }


}
