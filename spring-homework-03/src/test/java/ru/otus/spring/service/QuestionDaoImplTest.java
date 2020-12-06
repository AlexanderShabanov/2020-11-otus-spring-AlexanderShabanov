package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.config.CommonPropertiesConfig;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.dao.QuestionDaoImpl;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Александр Шабанов
 */
@DisplayName("Сервис чтения данных из файла должен")
class QuestionDaoImplTest {
  private  QuestionDao dao;
  @BeforeEach
  void BeforeAll(){
    dao = new QuestionDaoImpl("questions_ru_RU.csv");
  }
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