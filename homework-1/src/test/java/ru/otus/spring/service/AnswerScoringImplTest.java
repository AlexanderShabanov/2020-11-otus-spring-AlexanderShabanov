package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswerPair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Александр Шабанов
 */
class AnswerScoringImplTest {
  AnswerScoring answerScoring;
  Collection<QuestionAnswerPair> questionAnswerPairCollection;

  @BeforeEach
  void setUp() {
    Question question1 = new Question("Сколько будет 2*2", List.of(
        new Answer("1", false),
        new Answer("2", false),
        new Answer("4", true)
    ));
    Question question2 = new Question("Сколько бит в байте", List.of(
        new Answer("6", false),
        new Answer("8", true),
        new Answer("7", false)
    ));
    Answer answer1 = new Answer("1",false);
    Answer answer2 = new Answer("8",false);

    QuestionAnswerPair questionAnswerPair1 = new QuestionAnswerPair(question1,List.of(answer1));
    QuestionAnswerPair questionAnswerPair2 = new QuestionAnswerPair(question2,List.of(answer2));
    questionAnswerPairCollection = List.of(questionAnswerPair1,questionAnswerPair2);
    answerScoring = new AnswerScoringImpl();
  }

  @Test
  void getScore() {
    long mark = answerScoring.getScore(questionAnswerPairCollection);
    assertNotNull(mark);
    assertEquals(-10, mark);
  }
}