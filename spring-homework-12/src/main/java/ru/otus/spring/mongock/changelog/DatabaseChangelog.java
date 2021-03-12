package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.models.*;
import ru.otus.spring.repositories.*;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    private static final String[] USER_ROLE = {"USER"};
    private static final String[] ADMIN_AND_USER_ROLE = {"USER", "ADMIN"};
    private final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

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
        repository.save(new User("1", "user12", encoder.encode("user12Password"), USER_ROLE));
        repository.save(new User("2", "admin", encoder.encode("adminPassword"), ADMIN_AND_USER_ROLE));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "ashabanov")
    public void insertBooks(BookRepository repository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        Author dyuma = authorRepository.findById("2").orElseThrow();
        Genre comedy = genreRepository.findById("1").orElseThrow();
        Genre somethingElse = genreRepository.findById("3").orElseThrow();
        repository.save(new Book("1", dyuma, List.of(comedy, somethingElse), "Три мушкетера"));
    }

    @ChangeSet(order = "005", id = "insertBooks2", author = "ashabanov")
    public void insertBooks2(BookRepository repository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        Author pushkin = authorRepository.findById("1").orElseThrow();
        Genre tragedy = genreRepository.findById("2").orElseThrow();
        Genre somethingElse = genreRepository.findById("3").orElseThrow();
        repository.save(new Book("2", pushkin, List.of(tragedy, somethingElse), "Еще книжка"));
    }

    @ChangeSet(order = "006", id = "insertComments", author = "ashabanov")
    public void insertComments(CommentRepository repository, BookRepository bookRepository, UserRepository userRepository) {
        Book book2 = bookRepository.findById("2").orElseThrow();
        User user1 = userRepository.findById("1").orElseThrow();
        repository.save(new Comment("1", user1, "крутой блокбастер!!", book2));
    }
}
