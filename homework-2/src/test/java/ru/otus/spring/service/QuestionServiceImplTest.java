package ru.otus.spring.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Score;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис тестирования должен")
class QuestionServiceImplTest {
    private static final String OUTPUT_TEST_STRING = "тестовая строка";
    private static final String INPUT_TEST_LINES = "4\n6,2\n";
    private static final String INPUT_TEST_STRING = "4";

    private final Collection<Question> questionCollection = new ArrayList<>();
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream out = new PrintStream(outputStream);
    private final InputStream in = new ByteArrayInputStream(INPUT_TEST_LINES.getBytes());
    private final UserInterfaceService userInterfaceService = new UserInterfaceServiceImpl(out, in);

    private QuestionService questionService;
    @Mock
    private QuestionDao dao;
    @Mock
    private AnswerScoring answerScoring;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceImpl(dao, answerScoring, userInterfaceService);
        questionCollection.add(new Question("Сколько будет 2*2", List.of(
                new Answer("1", false),
                new Answer("2", false),
                new Answer("4", true)
        )));
//        questionCollection.add(new Question("Сколько будет 3*3", List.of(
//                new Answer("1", false),
//                new Answer("9", true),
//                new Answer("4", false)
//        )));

    }

    @AfterEach
    @SneakyThrows
    void after() {
        in.close();
        out.close();
        outputStream.close();
    }

    @DisplayName("корректно обрабатывать верные ответы")
    @Test
    void shouldCorrectReturnMark() {
        //TODO не понял, как корректно подсунуть ответ на второй вопрос.
        // не совсем понятна ценность теста, может лучше интеграционный ну или проверять все аргументы.
        when(dao.getQuestionCollection()).thenReturn(questionCollection);
        when(answerScoring.getScore(any())).thenReturn(new Score(20, true));
        assertDoesNotThrow(()->{questionService.run();});
        verify(dao,times(1)).getQuestionCollection();
        verify(answerScoring,times(1)).getScore(any());
    }
}