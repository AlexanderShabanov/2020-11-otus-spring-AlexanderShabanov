package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import ru.otus.spring.models.*;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "insertAuthors", author = "ashabanov")
    public void insertAuthors(MongockTemplate template) {
        template.save(new Author("1", "Александр", "Пушкин"));
        template.save(new Author("2", "Александр", "Дюма"));
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "ashabanov")
    public void insertGenres(MongockTemplate template) {
        template.save(new Genre("1", "комедия"));
        template.save(new Genre("2", "трагедия"));
        template.save(new Genre("3", "непонятная хрень"));
    }

    @ChangeSet(order = "003", id = "insertUsers", author = "ashabanov")
    public void insertUsers(MongockTemplate template) {
        template.save(new User("1", "user12"));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "ashabanov")
    public void insertBooks(MongockTemplate template) {
        Author dyuma = template.findById("2", Author.class);
        Genre comedy = template.findById("1", Genre.class);
        Genre somethingElse = template.findById("3", Genre.class);
        template.save(new Book("1", dyuma, List.of(comedy, somethingElse), "Три мушкетера"));
    }

    @ChangeSet(order = "005", id = "insertBooks2", author = "ashabanov")
    public void insertBooks2(MongockTemplate template) {
        Author pushkin = template.findById("1", Author.class);
        Genre tragedy = template.findById("2", Genre.class);
        Genre somethingElse = template.findById("3", Genre.class);
        template.save(new Book("2", pushkin, List.of(tragedy, somethingElse), "Еще книжка"));
    }

    @ChangeSet(order = "006", id = "insertComments", author = "ashabanov")
    public void insertComments(MongockTemplate template) {
        Book book2 = template.findById("2", Book.class);
        User user1 = template.findById("1", User.class);
        template.save(new Comment("1", user1, "крутой блокбастер!!", book2));
    }
}
