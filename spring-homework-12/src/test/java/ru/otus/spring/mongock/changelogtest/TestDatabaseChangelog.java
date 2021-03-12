package ru.otus.spring.mongock.changelogtest;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.models.*;
import ru.otus.spring.repositories.*;

import java.util.List;

@ChangeLog
public class TestDatabaseChangelog {
    private static final String[] USER_ROLE = {"USER"};
    private final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @ChangeSet(order = "000", id = "dropDb", author = "ashabanov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "001", id = "insertAuthorsTest", author = "ashabanov")
    public void insertAuthors(AuthorRepository repository) {
        repository.save(new Author("1", "Александр", "Пушкин"));
        repository.save(new Author("2", "Александр", "Дюма"));
    }

    @ChangeSet(order = "002", id = "insertGenresTest", author = "ashabanov")
    public void insertGenres(GenreRepository repository) {
        repository.save(new Genre("1", "комедия"));
        repository.save(new Genre("2", "трагедия"));
        repository.save(new Genre("3", "непонятная хрень"));
        repository.save(new Genre("4", "сказка"));
    }

    @ChangeSet(order = "003", id = "insertUsersTest", author = "ashabanov")
    public void insertUsers(UserRepository repository) {
        repository.save(new User("1", "user1", encoder.encode("password"), USER_ROLE));
        repository.save(new User("2", "user2", encoder.encode("veryStrongPassword"), USER_ROLE));
    }

    @ChangeSet(order = "004", id = "insertBooksTest", author = "ashabanov")
    public void insertBooks(BookRepository repository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        Author pushkin = authorRepository.findById("1").orElseThrow();
        Author dyuma = authorRepository.findById("2").orElseThrow();
        Genre comedy = genreRepository.findById("1").orElseThrow();
        Genre tragedy = genreRepository.findById("2").orElseThrow();
        Genre somethingElse = genreRepository.findById("3").orElseThrow();
        Genre fairyTale = genreRepository.findById("4").orElseThrow();
        repository.save(new Book("1", dyuma, List.of(tragedy), "Три мушкетера"));
        repository.save(new Book("2", dyuma, List.of(comedy, fairyTale), "Три поросенка"));
        repository.save(new Book("3", pushkin, List.of(somethingElse), "Книжка"));
    }

    @ChangeSet(order = "005", id = "insertCommentsTest", author = "ashabanov")
    public void insertComments(CommentRepository repository, BookRepository bookRepository, UserRepository userRepository) {
        Book book2 = bookRepository.findById("2").orElseThrow();
        User user1 = userRepository.findById("1").orElseThrow();
        User user2 = userRepository.findById("2").orElseThrow();
        repository.save(new Comment("1", user1, "крутой блокбастер!!", book2));
        repository.save(new Comment("2", user2, "шняга полная", book2));
    }

}
