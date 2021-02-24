package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.models.User;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;
import ru.otus.spring.repositories.UserRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "insertAuthors", author = "ashabanov")
    public void insertAuthors(AuthorRepository repository) {
        repository.save(new Author("1", "Александр", "Пушкин"));
        repository.save(new Author("2", "Александр", "Дюма"));
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "ashabanov")
    public void insertGenres(GenreRepository repository) {
        repository.save(new Genre("1", "комедия"));
        repository.save(new Genre("2", "трагедия"));
        repository.save(new Genre("3", "непонятная хрень"));
    }

    @ChangeSet(order = "003", id = "insertUsers", author = "ashabanov")
    public void insertUsers(UserRepository repository) {
        repository.save(new User("1", "user12"));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "ashabanov")
    public void insertBooks(BookRepository repository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        Author dyuma = authorRepository.findById("2").orElseThrow();
        Genre comedy = genreRepository.findById("1").orElseThrow();
        Genre somethingElse = genreRepository.findById("3").orElseThrow();
        repository.save(new Book("1", dyuma, List.of(comedy, somethingElse), "Три мушкетера"));
    }

}
