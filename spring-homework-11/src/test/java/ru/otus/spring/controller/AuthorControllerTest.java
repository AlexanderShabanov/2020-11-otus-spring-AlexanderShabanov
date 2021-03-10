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
import ru.otus.spring.models.Author;
import ru.otus.spring.repositories.AuthorRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest(properties = "mongock.enabled=false", controllers = AuthorController.class)
@DisplayName("AuthorController должен:")
public class AuthorControllerTest {
    private static final int AUTHORS_QUANTITY = 2;
    private static final Author kruzenshtern = new Author("1", "Иван Федорович", "Крузенштерн");
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Отдавать всех авторов")
    void listAuthors() {
        Flux<Author> kruzenshternFlux = Flux.just(kruzenshtern);
        Mockito.when(authorRepository.findAll()).thenReturn(kruzenshternFlux);
        webTestClient.get()
                .uri("/api/v2/author")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Author.class)
                .value(value -> {
                    assertThat(value.size() == AUTHORS_QUANTITY);
                    assertEquals(kruzenshtern, value.get(0));
                });

    }
}
