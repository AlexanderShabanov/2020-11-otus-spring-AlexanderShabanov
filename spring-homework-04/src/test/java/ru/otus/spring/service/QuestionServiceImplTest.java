package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswerPair;
import ru.otus.spring.domain.Score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = QuestionServiceImpl.class)
@DisplayName("Сервис тестирования должен")
class QuestionServiceImplTest {
  private static final String[] answers = {"4\n", "9,1\n"};
  private static final String OUT_STRING = "Вы набрали %s баллов. Тест %s";
  private static final String NOT_COMPLETED = " не пройден!";

  private final Collection<Question> questionCollection = new ArrayList<>();

  @MockBean
  private UserInterfaceService userInterfaceService;

  @MockBean
  private QuestionDao dao;
  @MockBean
  private AnswerScoring answerScoring;

  @MockBean
  private AskQuestionService askQuestionService;

  @MockBean
  private LocalizedMessageSource messageSource;

  @Autowired
  private QuestionService questionService;
  private final Question[] questions = {
      new Question("Сколько будет 2*2", List.of(
          new Answer("1", false),
          new Answer("2", false),
          new Answer("4", true)
      )),
      new Question("Сколько будет 3*3", List.of(
          new Answer("1", false),
          new Answer("9", true),
          new Answer("4", false)
      ))
  };

  @BeforeEach
  void setUp() {
    questionCollection.add(questions[0]);
    questionCollection.add(questions[1]);

  }


  @DisplayName("корректно обрабатывать верные ответы")
  @Test
  void shouldCorrectReturnMark() {
    when(messageSource.getMessage("question-service.you-got-mark")).thenReturn(OUT_STRING);
    when(messageSource.getMessage("question-service.not-done")).thenReturn(NOT_COMPLETED);

    Score score = new Score(10, false);
    QuestionAnswerPair[] questionAnswerPairs = {new QuestionAnswerPair(questions[0],
        List.of(new Answer(answers[0], false))), new QuestionAnswerPair(questions[1],
        List.of(new Answer(answers[1], false)))};
    when(askQuestionService.proceedQuestion(questions[0])).thenReturn(questionAnswerPairs[0]);
    when(askQuestionService.proceedQuestion(questions[1])).thenReturn(questionAnswerPairs[1]);
    when(dao.getQuestionCollection()).thenReturn(questionCollection);
    when(answerScoring.getScore(Arrays.asList(questionAnswerPairs))).thenReturn(score);
    assertDoesNotThrow(() -> {
      questionService.run();
    });
    verify(dao, times(1)).getQuestionCollection();
    verify(answerScoring, times(1)).getScore(any());
    verify(userInterfaceService, times(1)).textOut(OUT_STRING, score.getMark(), NOT_COMPLETED);
  }
}