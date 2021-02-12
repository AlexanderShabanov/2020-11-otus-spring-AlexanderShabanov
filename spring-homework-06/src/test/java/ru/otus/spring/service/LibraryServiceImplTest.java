package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.models.User;
import ru.otus.spring.repositories.*;
import ru.otus.spring.service.exception.RelatedObjectNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Библиотечный сервис должен")
@SpringBootTest
@ContextConfiguration(classes = LibraryServiceImpl.class)
@ExtendWith(MockitoExtension.class)
class LibraryServiceImplTest {
    private static final long BOOK_ID = 1;
    private static final long AUTHOR_ID = 1;
    private static final String AUTHOR_NAME = "Александр";
    private static final String AUTHOR_SURNAME = "Пушкин";
    private static final long GENRE_ID = 1;
    private static final String GENRE_NAME = "комедия";
    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "user11";
    private static final String COMMENT_TEXT = "коммент";
    private static final Long COMMENT_ID = 3L;
    private Book book1;

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


    @Test
    @DisplayName("найти книгу по ID")
    void shouldSuccessfullyFindBookById() {
        when(bookRepository.findBookById(BOOK_ID)).thenReturn(Optional.of(book1));
        Optional<Book> foundBook = libraryService.findBookById(BOOK_ID);
        assertNotNull(foundBook);
        assertFalse(foundBook.isEmpty());
        assertEquals(book1, foundBook.get());
    }

    @Test
    @DisplayName("добавить книгу со ссылкой на существующего автора и жанр только по ID")
    void shouldSuccessfullyInsertBookWithIdReferencesOnly() {
        book1.getAuthor().setName(null);
        book1.getAuthor().setSurName(null);
        book1.getGenre().get(0).setName(null);
        when(authorRepository.checkAuthorExistsById(AUTHOR_ID)).thenReturn(true);
        when(genreRepository.checkGenreExistsById(GENRE_ID)).thenReturn(true);
        libraryService.insertBook(book1, false);
        verify(bookRepository, times(1)).insertBook(book1);
    }

    @Test
    @DisplayName("выкинуть exception при добавлении книги со ссылкой на несуществующего автора и жанр только по ID")
    void shouldThrowExceptionInsertBookWithIdReferencesOnly() {
        book1.getAuthor().setName(null);
        book1.getAuthor().setSurName(null);
        book1.getGenre().get(0).setName(null);
        when(authorRepository.findAuthorById(AUTHOR_ID)).thenReturn(Optional.ofNullable(null));
        when(genreRepository.findGenreById(GENRE_ID)).thenReturn(Optional.of(new Genre()));
        assertThatThrownBy(() -> libraryService.insertBook(book1, false)).isInstanceOf(RelatedObjectNotFoundException.class);
    }

    @Test
    @DisplayName("добавить книгу со ссылкой не существующего автора и жанр при передаче сущности целиком")
    void shouldSuccessfulInsertBookWithFullRelatedEntitiesReferences() {
        when(authorRepository.findAuthorById(AUTHOR_ID)).thenReturn(Optional.ofNullable(null));
        when(genreRepository.findGenreById(GENRE_ID)).thenReturn(Optional.ofNullable(null));
        libraryService.insertBook(book1, true);
        verify(bookRepository, times(1)).insertBook(book1);
    }

    @Test
    @DisplayName("добавлять новый комментарий к книге")
    void shouldAddNewCommentForBook() {
        User user = new User(USER_ID, USER_NAME);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(bookRepository.findBookById(BOOK_ID)).thenReturn(Optional.of(book1));
        when(commentRepository.save(any())).thenReturn(COMMENT_ID);
        var commentId = libraryService.saveComment(null, BOOK_ID, USER_ID,COMMENT_TEXT);
        assertNotNull(commentId);
    }

    @BeforeEach
    void setUp() {
        Author author = new Author(AUTHOR_ID, AUTHOR_NAME, AUTHOR_SURNAME);
        Genre genre = new Genre(GENRE_ID, GENRE_NAME);
        book1 = Book.builder().id(BOOK_ID).author(author).genre(List.of(genre)).name("книжка").build();
    }
}