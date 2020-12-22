package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswerPair;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Александр Шабанов
 */
@SpringBootTest
@DisplayName("Сервис задавания вопросов и получения ответов должен")
class AskQuestionServiceImplTest {
  private static final String IDENT = "    ";
  private static final String STRING1 = "%s %s\n";
  private static final String ANSWERTEXT1 = "1";
  private static final String ANSWERTEXT2 = "2";
  private static final String ANSWERTEXT3 = "4";
  private static final String MESSAGE1 = "Вопросы могут иметь несколько правильных ответов. Значения при ответе разделяются запятой.";
  private static final String MESSAGE2 = "%s Варианты ответов:";


  private final AskQuestionService askQuestionService;
  @MockBean
  private LocalizedUserInterfaceService userInterfaceService;
  @Autowired
  AskQuestionServiceImplTest(AskQuestionService askQuestionService) {
    this.askQuestionService = askQuestionService;
  }

//  @BeforeEach
//  void setUp() {
//    askQuestionService = new AskQuestionServiceImpl(userInterfaceService);
//  }

  @Test
  @DisplayName("как это ни странно, уметь задать вопрос и получить на него ответ")
  void shouldCorrectAskQuestionAndGetAnswer() {
    Question question = new Question("Сколько будет 2*2", List.of(
        new Answer(ANSWERTEXT1, false),
        new Answer(ANSWERTEXT2, false),
        new Answer(ANSWERTEXT3, true)
    ));
    QuestionAnswerPair questionAnswerPairExpected = new QuestionAnswerPair(question, List.of(new Answer(ANSWERTEXT3, false)));
    when(userInterfaceService.textIn()).thenReturn(ANSWERTEXT3);
    QuestionAnswerPair questionAnswerPair = askQuestionService.proceedQuestion(question);
    verify(userInterfaceService, times(1)).textOut(question.getQuestionText());
    verify(userInterfaceService, times(1)).textOut(STRING1, IDENT, ANSWERTEXT1);
    verify(userInterfaceService, times(1)).textOut(STRING1, IDENT, ANSWERTEXT2);
    verify(userInterfaceService, times(1)).textOut(STRING1, IDENT, ANSWERTEXT3);
    verify(userInterfaceService, times(1)).textIn();
    assertEquals(questionAnswerPairExpected, questionAnswerPair);
  }


}