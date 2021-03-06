package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.dao.QuestionDaoImpl;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Александр Шабанов
 */
@DisplayName("Сервис чтения данных из файла должен")
class QuestionDaoImplTest {
  private final QuestionDao dao = new QuestionDaoImpl("questions.csv");

  @Test
  @DisplayName("вернуть корректные данные из файла")
  void shouldCorrectReturnData() {
    Question question1 = new Question("Сколько будет 2*2", List.of(
        new Answer("1", false),
        new Answer("2", false),
        new Answer("4", true)
    ));
    Collection<Question> questionCollection = dao.getQuestionCollection();
    assertNotNull(questionCollection);
    assertEquals(2, questionCollection.size());
    assertTrue(questionCollection.contains(question1));
  }
}