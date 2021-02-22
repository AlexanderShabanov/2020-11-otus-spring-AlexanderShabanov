package ru.otus.spring.repositories;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.models.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@DisplayName("Репозиторий комментариев должен:")
class CommentRepositoryJpaImplTest {
    private static final short COMMENTS_NUMBER = 2;
    private static final String COMMENT_1_TEXT = "крутой блокбастер!!";
    private static final String USER1_NAME = "user1";
    private static final Long COMMENT_ID = 1L;
    private static final Long COMMENT2_ID = 2L;
    public static final String UPDATED_COMMENT_TEXT = "измененный коммент";
    private static final Long BOOK_ID = 2L;
    private static final long EXPECTED_QUERIES_COUNT = 1L;
    @Autowired
    private TestEntityManager em;

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("находить все комментарии")
    void shouldFindAll() {
        var comments = commentRepository.findAll();
        assertNotNull(comments);
        assertFalse(comments.isEmpty());
        assertEquals(COMMENTS_NUMBER, comments.size());
        assertEquals(COMMENT_1_TEXT, comments.get(0).getCommentText());
        assertEquals(BOOK_ID, comments.get(0).getBook().getId());
        assertEquals(USER1_NAME, comments.get(0).getUser().getName());
    }

    @Test
    @DisplayName("находить комментарий по id")
    void shouldFindById() {
        var comment = commentRepository.findById(COMMENT_ID);
        assertTrue(comment.isPresent());
        assertEquals(COMMENT_1_TEXT, comment.get().getCommentText());
        assertEquals(USER1_NAME, comment.get().getUser().getName());
    }

    @Test
    @DisplayName("сохранять новый коммент")
    void shouldSaveNew() {
        var id = commentRepository.save(new Comment(null, new User(), "коммент",new Book(1L, new Author(), List.of(new Genre()), "книга")));
        assertNotNull(id);
    }

    @Test
    @DisplayName("сохранять новый коммент")
    void shouldFindByIdAndUpdateExisted() {
        var comment = commentRepository.findById(COMMENT2_ID)
                .orElseThrow(() -> new EntityNotFoundException(String.format("коммент с ID = %d не найден", COMMENT2_ID)));
        comment.setCommentText(UPDATED_COMMENT_TEXT);
        var id = commentRepository.save(comment).getId();
        assertNotNull(id);
        comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("коммент с ID = %d не найден", COMMENT2_ID)));
        assertEquals(UPDATED_COMMENT_TEXT, comment.getCommentText());
    }

    @Test
    @DisplayName("найти комментарии по id книги")
    void shouldFindAllCommentsByBookId(){
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().clear();
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        var comments = commentRepository.findByBookId(BOOK_ID);
        comments.forEach(System.out::println);
        assertEquals(COMMENTS_NUMBER, comments.size());
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);

    }
}