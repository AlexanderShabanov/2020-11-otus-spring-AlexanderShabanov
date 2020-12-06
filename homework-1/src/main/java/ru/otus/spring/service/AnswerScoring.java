package ru.otus.spring.service;

import ru.otus.spring.domain.QuestionAnswerPair;

import java.util.Collection;

/**
 * @author Александр Шабанов
 */
public interface AnswerScoring {
  public int getScore(Collection<QuestionAnswerPair> questionAnswerPairCollection);
}
