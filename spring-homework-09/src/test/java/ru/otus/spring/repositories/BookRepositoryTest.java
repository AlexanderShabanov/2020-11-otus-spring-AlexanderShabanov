package ru.otus.spring.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.events.CommentCascadeDeleteMongoEventListener;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@DisplayName("Репозиторий для книг должен")
@Import(CommentCascadeDeleteMongoEventListener.class)
class BookRepositoryTest {
    private static final short BOOKS_NUMBER = 3;
    private static final String BOOK_ID = "1";
    private static final String BOOK_NAME = "Три мушкетера";
    private static final String AUTHOR_ID = "2";
    private static final String AUTHOR_NAME = "Александр";
    private static final String AUTHOR_SURNAME = "Дюма";
    private static final String GENRE_ID = "2";
    private static final String GENRE_NAME = "трагедия";
    private static final String BOOK_ID_2 = "2";
    private static final String AUTHOR_ID_1 = "1";
    private static final String BOOK_NAME_2 = "книга2";
    private static final long EXPECTED_QUERIES_COUNT = 2L;
    private static final String AUTHOR3_NAME = "Автор";
    private static final String AUTHOR3_SURNAME = "Авторов";
    private static final short COMMENTS_NUMBER = 2;
    private final Author author = new Author(AUTHOR_ID, AUTHOR_NAME, AUTHOR_SURNAME);
    private final Author author3 = new Author(null, AUTHOR3_NAME, AUTHOR3_SURNAME);
    private final Genre genre = new Genre(GENRE_ID, GENRE_NAME);
    private final Book book1 = Book.builder().id(BOOK_ID).name(BOOK_NAME).author(author).genre(List.of(genre)).build();//.comments(Collections.emptyList())
    private final Author author2 = new Author(null, AUTHOR_NAME, AUTHOR_SURNAME);
    private final Genre genre2 = new Genre(null, GENRE_NAME);

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("загружать список всех книг")
    void shouldFindAllBooks() {

        List<Book> books = bookRepository.findAll();

        System.out.println(books);
        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals(BOOKS_NUMBER, books.size());
        assertEquals(book1, books.get(0));
    }

    @Test
    @DisplayName("находить книгу по ID")
    void shouldFindBookById() {
        Optional<Book> bookOptional = bookRepository.findById(BOOK_ID);
        assertNotNull(bookOptional);
        assertEquals(AUTHOR_NAME, bookOptional.get().getAuthor().getName()); //а пусть будет мало ли Equals поломается
        assertEquals(book1, bookOptional.get());
    }

    @Test
    @DisplayName("сохранить, а потом загрузить информацию о книге")
    void ShouldInsertAndLoadCorrectBook() {
        Book book2 = Book.builder().id(null).name("книга2").author(author).genre(List.of(genre)).build();
        String savedBookId = bookRepository.save(book2).getId();
        Optional<Book> optionalBook = Optional.of(mongoOperations.findById(savedBookId, Book.class));
        assertTrue(optionalBook.isPresent());
        assertEquals(book2, optionalBook.get());
    }

    @Test
    @DisplayName("изменить, а потом загрузить информацию о книге")
    void shouldUpdateAndLoadCorrectBook() {
        Author author1 = new Author(AUTHOR_ID_1, "Александр", "Пушкин");
        Book book2 = Book.builder().id(BOOK_ID).name(BOOK_NAME_2).author(author1).genre(List.of(genre)).build();
        bookRepository.save(book2); // теперь книга с ID = 1 - не три мушкетера, а книга 2
        Optional<Book> optionalBook = Optional.of(mongoOperations.findById(BOOK_ID, Book.class));
        assertTrue(optionalBook.isPresent());
        assertEquals(BOOK_NAME_2, optionalBook.get().getName());
        assertEquals(author1, optionalBook.get().getAuthor());

    }

    @Test
    void shouldDeleteBook() {
        assertEquals(COMMENTS_NUMBER, mongoOperations.findAll(Comment.class).size());
        bookRepository.deleteById(BOOK_ID_2);
        assertEquals(Collections.emptyList(), mongoOperations.findAll(Comment.class));
        assertTrue(mongoOperations.findAll(Comment.class).isEmpty());
        Optional<Book> optionalBook = bookRepository.findById(BOOK_ID_2);
        assertFalse(optionalBook.isPresent());
    }
}