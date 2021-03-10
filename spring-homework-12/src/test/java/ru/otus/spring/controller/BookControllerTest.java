package ru.otus.spring.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.LibraryService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class, properties = "mongock.enabled=false")
@ContextConfiguration(classes = BookController.class)
@DisplayName("BookController должен:")
public class BookControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  LibraryService libraryService;

  private static final String BOOK_ID = "1";
  private static final String AUTHOR_ID = "1";
  private static final String AUTHOR_NAME = "Александр";
  private static final String AUTHOR_SURNAME = "Пушкин";
  private static final String GENRE_ID = "1";
  private static final String GENRE_NAME = "комедия";
  private final Author author = new Author(AUTHOR_ID, AUTHOR_NAME, AUTHOR_SURNAME);
  private final Genre genre = new Genre(GENRE_ID, GENRE_NAME);
  private final Book book1 = Book.builder().id(BOOK_ID).author(author).genre(List.of(genre)).name("книжка").build();
  private final BookDto book1Dto = new ModelMapper().map(book1, BookDto.class);

  @DisplayName("Успешно вернуть все книги для авторизированного пользователя")
  @Test
  @SneakyThrows
  @WithMockUser(roles = "USER")
  void getAllBooks() {
    Mockito.when(libraryService.findAllBooks()).thenReturn(List.of(book1Dto));
    mockMvc.perform(get("/api/v1/book").contentType(MediaType.APPLICATION_JSON)).andDo(print())
            .andExpect(status().isOk());
  }

  @DisplayName("Вернуть ошибку авторизации для несуществующего юзера")
  @Test
  @SneakyThrows
  @WithAnonymousUser
  void shouldReturnNotAuthorized() {
    Mockito.when(libraryService.findAllBooks()).thenReturn(List.of(book1Dto));
    mockMvc.perform(get("/api/v1/book").contentType(MediaType.APPLICATION_JSON)).andDo(print())
            .andExpect(status().isUnauthorized());
  }
}
