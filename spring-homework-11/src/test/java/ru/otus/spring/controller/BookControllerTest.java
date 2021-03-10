package ru.otus.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebFluxTest(controllers = BookController.class, properties = "mongock.enabled=false")
@DisplayName("BookController должен:")
public class BookControllerTest {
    private static final int BOOKS_QUANTITY = 2;
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private CommentRepository commentRepository;

    private static final short BOOKS_NUMBER = 3;
    private static final String BOOK_ID = "1";
    private static final String BOOK_NAME = "Три мушкетера";
    private static final String BOOK_NAME2 = "Три поросенка";
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
    private final Book book1 = Book.builder().id(BOOK_ID).name(BOOK_NAME).author(author).genre(List.of(genre)).build();
    private final Author author2 = new Author(null, AUTHOR_NAME, AUTHOR_SURNAME);
    private final Genre genre2 = new Genre(null, GENRE_NAME);
    private final Book book2 = Book.builder().id(BOOK_ID_2).name(BOOK_NAME2).author(author).genre(List.of(genre, genre2)).build();

    @Test
    @DisplayName("Отдавать все книги библиотеки")
    void listBooks() {
        Flux<Book> booksFlux = Flux.just(book1, book2);
        Mockito.when(bookRepository.findAll()).thenReturn(booksFlux);
        webTestClient.get()
                .uri("/api/v2/book")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .value(value -> {
                    assertThat(value.size() == BOOKS_QUANTITY);
                    assertEquals(book1, value.get(0));
                    assertEquals(book2, value.get(1));
                });
    }

    @Test
    @DisplayName("Возвращать книгу по ID")
    void findBookById() {
        Mono<Book> bookMono = Mono.just(book1);
        Mockito.when(bookRepository.findById(BOOK_ID)).thenReturn(bookMono);
        webTestClient.get()
                .uri("/api/v2/book/" + BOOK_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .value(value -> {
                    assertThat(value.getId() == BOOK_ID);
                    assertEquals(book1, value);
                });
    }

    @Test
    @DisplayName("Добавлять книгу")
    void addBook() {
        Mockito.when(bookRepository.save(book1)).thenReturn(Mono.just(book1));
        webTestClient.post()
                .uri("/api/v2/book/")
                .bodyValue(book1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .value(value -> {
                    assertThat(value.getId() == BOOK_ID);
                    assertEquals(book1, value);
                });
        verify(bookRepository, times(1)).save(book1);

    }

    @Test
    @DisplayName("Изменять книгу")
    void editBook() {
        Mockito.when(bookRepository.save(book1)).thenReturn(Mono.just(book1));
        webTestClient.put()
                .uri("/api/v2/book/")
                .bodyValue(book1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .value(value -> {
                    assertThat(value.getId() == BOOK_ID);
                    assertEquals(book1, value);
                });
        verify(bookRepository, times(1)).save(book1);

    }

    @Test
    @DisplayName("Удалять книгу")
    void deleteBook() {
        Mockito.when(bookRepository.save(book1)).thenReturn(Mono.just(book1));
        webTestClient.delete()
                .uri("/api/v2/book/" + BOOK_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        verify(bookRepository, times(1)).deleteById(BOOK_ID);
    }
}
