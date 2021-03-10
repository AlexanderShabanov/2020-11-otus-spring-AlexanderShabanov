package ru.otus.spring.mongock.changelogtest;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.models.*;

import java.util.List;

@ChangeLog
public class TestDatabaseChangelog {
    @ChangeSet(order = "000", id = "dropDb", author = "ashabanov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "001", id = "insertAuthorsTest", author = "ashabanov")
    public void insertAuthors(MongockTemplate template) {
        template.save(new Author("1", "Александр", "Пушкин"));
        template.save(new Author("2", "Александр", "Дюма"));
    }

    @ChangeSet(order = "002", id = "insertGenresTest", author = "ashabanov")
    public void insertGenres(MongockTemplate template) {
        template.save(new Genre("1", "комедия"));
        template.save(new Genre("2", "трагедия"));
        template.save(new Genre("3", "непонятная хрень"));
        template.save(new Genre("4", "сказка"));
    }

    @ChangeSet(order = "003", id = "insertUsersTest", author = "ashabanov")
    public void insertUsers(MongockTemplate template) {
        template.save(new User("1", "user1"));
        template.save(new User("2", "user2"));
    }

    @ChangeSet(order = "004", id = "insertBooksTest", author = "ashabanov")
    public void insertBooks(MongockTemplate template) {
        Author pushkin = template.findById("1", Author.class);
        Author dyuma = template.findById("2", Author.class);
        Genre comedy = template.findById("1", Genre.class);
        Genre tragedy = template.findById("2", Genre.class);
        Genre somethingElse = template.findById("3", Genre.class);
        Genre fairyTale = template.findById("4", Genre.class);
        template.save(new Book("1", dyuma, List.of(tragedy), "Три мушкетера"));
        template.save(new Book("2", dyuma, List.of(comedy, fairyTale), "Три поросенка"));
        template.save(new Book("3", pushkin, List.of(somethingElse), "Книжка"));
    }

    @ChangeSet(order = "005", id = "insertCommentsTest", author = "ashabanov")
    public void insertComments(MongockTemplate template) {
        Book book2 = template.findById("2", Book.class);
        User user1 = template.findById("1", User.class);
        User user2 = template.findById("2", User.class);
        template.save(new Comment("1", user1, "крутой блокбастер!!", book2));
        template.save(new Comment("2", user2, "шняга полная", book2));
    }

}
