package ru.otus.spring.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.spring.models.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@DisplayName("Репозиторий комментариев должен:")
class CommentRepositoryTest {
    private static final short COMMENTS_NUMBER = 2;
    private static final String COMMENT_1_TEXT = "крутой блокбастер!!";
    private static final String USER1_NAME = "user1";
    private static final String COMMENT_ID = "1";
    private static final String COMMENT2_ID = "2";
    public static final String UPDATED_COMMENT_TEXT = "измененный коммент";
    private static final String BOOK_ID = "2";

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("находить все комментарии")
    void shouldFindAll() {
        Flux<Comment> commentsFlux = commentRepository.findAll();
        StepVerifier
                .create(commentsFlux)
                .assertNext(comment -> {
                    assertEquals(COMMENT_1_TEXT, comment.getCommentText());
                    assertEquals(BOOK_ID, comment.getBook().getId());
                    assertEquals(USER1_NAME, comment.getUser().getName());
                })
                .expectNextCount(COMMENTS_NUMBER - 1)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("находить комментарий по id")
    void shouldFindById() {
        var comment = commentRepository.findById(COMMENT_ID).block();
        assertEquals(COMMENT_1_TEXT, comment.getCommentText());
        assertEquals(USER1_NAME, comment.getUser().getName());
    }

    @Test
    @DisplayName("сохранять новый коммент")
    void shouldSaveNew() {
        var id = commentRepository.save(new Comment(null, new User("3", "Вася"), "коммент", new Book("5", new Author("6", "автор", "авторович"), List.of(new Genre("5", "жанр")), "книга"))).block();
        assertNotNull(id);
    }

    @Test
    @DisplayName("изменять коммент")
    void shouldFindByIdAndUpdateExisted() {
        var comment = commentRepository.findById(COMMENT2_ID).block();
        comment.setCommentText(UPDATED_COMMENT_TEXT);
        var id = commentRepository.save(comment).block().getId();
        assertNotNull(id);
        comment = commentRepository.findById(id).block();
        assertEquals(UPDATED_COMMENT_TEXT, comment.getCommentText());
    }

    @Test
    @DisplayName("найти комментарии по id книги")
    void shouldFindAllCommentsByBookId() {

        var commentsFlux = commentRepository.findByBookId(BOOK_ID);
        StepVerifier
                .create(commentsFlux)
                .expectNextCount(COMMENTS_NUMBER)
                .expectComplete()
                .verify();

    }
}