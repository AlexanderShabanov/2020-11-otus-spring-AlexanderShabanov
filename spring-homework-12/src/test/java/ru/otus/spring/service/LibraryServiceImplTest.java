package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.models.*;
import ru.otus.spring.repositories.*;
import ru.otus.spring.service.exception.RelatedObjectNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@DisplayName("Библиотечный сервис должен")
@SpringBootTest
@ContextConfiguration(classes = {LibraryServiceImpl.class, ModelMapper.class})
@ExtendWith(MockitoExtension.class)
class LibraryServiceImplTest {
    private static final String BOOK_ID = "1";
    private static final String AUTHOR_ID = "1";
    private static final String AUTHOR_NAME = "Александр";
    private static final String AUTHOR_SURNAME = "Пушкин";
    private static final String GENRE_ID = "1";
    private static final String GENRE_NAME = "комедия";
    private static final String USER_ID = "1";
    private static final String USER_NAME = "user11";
    private static final String COMMENT_TEXT = "коммент";
    private static final String COMMENT_ID = "3";
    private Book book1;
    private BookDto book1Dto;

    @Autowired
    private LibraryService libraryService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CommentRepository commentRepository;

    private final static String[] USER_ROLE = {"USER"};

    @Test
    @DisplayName("найти книгу по ID")
    void shouldSuccessfullyFindBookById() {
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book1));
        BookDto foundBook = libraryService.findBookById(BOOK_ID);
        assertNotNull(foundBook);
        assertThat(foundBook).usingRecursiveComparison().isEqualTo(book1);
        //assertEquals(book1, foundBook);
    }

    @Test
    @DisplayName("добавить книгу со ссылкой на существующего автора и жанр только по ID")
    void shouldSuccessfullyInsertBookWithIdReferencesOnly() {
        book1.getAuthor().setName(null);
        book1.getAuthor().setSurName(null);
        book1.getGenre().get(0).setName(null);
        book1Dto = new ModelMapper().map(book1, BookDto.class);
        when(authorRepository.existsById(AUTHOR_ID)).thenReturn(true);
        when(genreRepository.existsById(GENRE_ID)).thenReturn(true);
        when(bookRepository.save(book1)).thenReturn(book1);
        libraryService.insertBook(book1Dto, false);
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("выкинуть exception при добавлении книги со ссылкой на несуществующего автора и жанр только по ID")
    void shouldThrowExceptionInsertBookWithIdReferencesOnly() {
        book1.getAuthor().setName(null);
        book1.getAuthor().setSurName(null);
        book1.getGenre().get(0).setName(null);
        book1Dto = new ModelMapper().map(book1, BookDto.class);
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.ofNullable(null));
        when(genreRepository.findById(GENRE_ID)).thenReturn(Optional.of(new Genre()));
        assertThatThrownBy(() -> libraryService.insertBook(book1Dto, false)).isInstanceOf(RelatedObjectNotFoundException.class);
    }

    @Test
    @DisplayName("добавить книгу со ссылкой не существующего автора и жанр при передаче сущности целиком")
    void shouldSuccessfulInsertBookWithFullRelatedEntitiesReferences() {
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.ofNullable(null));
        when(genreRepository.findById(GENRE_ID)).thenReturn(Optional.ofNullable(null));
        when(bookRepository.save(book1)).thenReturn(book1);
        libraryService.insertBook(book1Dto, true);
        verify(bookRepository, times(1)).save(book1);
    }

    @Test
    @DisplayName("добавлять новый комментарий к книге")
    void shouldAddNewCommentForBook() {
        User user = new User(USER_ID, USER_NAME, "pswd", USER_ROLE);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book1));
        when(commentRepository.save(any())).thenReturn(new Comment("1", user, COMMENT_TEXT, book1));
        var commentId = libraryService.saveComment(null, BOOK_ID, USER_ID, COMMENT_TEXT);
        assertNotNull(commentId);
    }

    @BeforeEach
    void setUp() {
        Author author = new Author(AUTHOR_ID, AUTHOR_NAME, AUTHOR_SURNAME);
        Genre genre = new Genre(GENRE_ID, GENRE_NAME);
        book1 = Book.builder().id(BOOK_ID).author(author).genre(List.of(genre)).name("книжка").build();
        book1Dto = new ModelMapper().map(book1, BookDto.class);
    }
}