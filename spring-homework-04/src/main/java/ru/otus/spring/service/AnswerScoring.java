package ru.otus.spring.service;

import ru.otus.spring.domain.QuestionAnswerPair;
import ru.otus.spring.domain.Score;

import java.util.Collection;

/**
 * @author Александр Шабанов
 */
public interface AnswerScoring {
  Score getScore(Collection<QuestionAnswerPair> questionAnswerPairCollection);
}
