package ru.otus.spring.service;

import ru.otus.spring.domain.QuestionAnswerPair;
import ru.otus.spring.domain.Score;

import java.util.Collection;

/**
 * @author Александр Шабанов
 */
public interface AnswerScoring {
  public Score getScore(Collection<QuestionAnswerPair> questionAnswerPairCollection);
}
