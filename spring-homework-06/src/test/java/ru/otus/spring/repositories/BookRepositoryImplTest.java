package ru.otus.spring.repositories;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BookRepositoryJpaImpl.class)
@DisplayName("Репозиторий для книг должен")
class BookRepositoryImplTest {
  private static final short BOOKS_NUMBER = 3;
  private static final long BOOK_ID = 1;
  private static final String BOOK_NAME = "Три мушкетера";
  private static final long AUTHOR_ID = 2;
  private static final String AUTHOR_NAME = "Александр";
  private static final String AUTHOR_SURNAME = "Дюма";
  private static final long GENRE_ID = 2;
  private static final String GENRE_NAME = "трагедия";
  private static final long BOOK_ID_2 = 2;
  private static final long AUTHOR_ID_1 = 1;
  private static final String BOOK_NAME_2 = "книга2";
  private static final long EXPECTED_QUERIES_COUNT = 2L;
  private static final String AUTHOR3_NAME = "Автор";
  private static final String AUTHOR3_SURNAME = "Авторов";
  private final Author author = new Author(AUTHOR_ID, AUTHOR_NAME, AUTHOR_SURNAME);
  private final Author author3 = new Author(null, AUTHOR3_NAME, AUTHOR3_SURNAME);
  private final Genre genre = new Genre(GENRE_ID, GENRE_NAME);
  private final Book book1 = Book.builder().id(BOOK_ID).name(BOOK_NAME).author(author).genre(List.of(genre)).build();//.comments(Collections.emptyList())
  private final Author author2 = new Author(null, AUTHOR_NAME, AUTHOR_SURNAME);
  private final Genre genre2 = new Genre(null, GENRE_NAME);

  @Autowired
  private TestEntityManager em;
  @Autowired
  private BookRepository bookRepository;

  @Test
  @DisplayName("загружать список всех книг")
  void shouldFindAllBooks() {

    SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
        .unwrap(SessionFactory.class);
    sessionFactory.getStatistics().setStatisticsEnabled(true);

    var books = bookRepository.findAllBooks();

    System.out.println(books);
    assertNotNull(books);
    assertFalse(books.isEmpty());
    assertEquals(BOOKS_NUMBER, books.size());
    assertEquals(book1, books.get(0));
    assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
  }

  @Test
  @DisplayName("находить книгу по ID")
  void shouldFindBookById() {
    Optional<Book> bookOptional = bookRepository.findBookById(BOOK_ID);
    assertNotNull(bookOptional);
    assertEquals(AUTHOR_NAME, bookOptional.get().getAuthor().getName()); //а пусть будет мало ли Equals поломается
    assertEquals(book1, bookOptional.get());
  }

  @Test
  @DisplayName("сохранить, а потом загрузить информацию о книге")
  void ShouldInsertAndLoadCorrectBook() {
    Book book2 = Book.builder().id(null).name("книга2").author(author).genre(List.of(genre)).build();
    long savedBookId = bookRepository.insertBook(book2);
    Optional<Book> optionalBook = bookRepository.findBookById(savedBookId);
    assertTrue(optionalBook.isPresent());
    assertEquals(book2, optionalBook.get());
  }

  @Test
  @DisplayName("изменить, а потом загрузить информацию о книге")
  void shouldUpdateAndLoadCorrectBook() {
    Author author1 = new Author(AUTHOR_ID_1, "Александр", "Пушкин");
    Book book2 = Book.builder().id(BOOK_ID).name(BOOK_NAME_2).author(author1).genre(List.of(genre)).build();
    bookRepository.updateBook(book2); // теперь книга с ID = 1 - не три мушкетера, а книга 2
    Optional<Book> optionalBook = bookRepository.findBookById(BOOK_ID);
    assertTrue(optionalBook.isPresent());
    assertEquals(BOOK_NAME_2, optionalBook.get().getName());
    assertEquals(author1, optionalBook.get().getAuthor());

  }

  @Test
  void shouldDeleteBook() {
    bookRepository.deleteBookById(BOOK_ID);
    Optional<Book> optionalBook = bookRepository.findBookById(BOOK_ID);
    assertFalse(optionalBook.isPresent());
  }
}