package ru.otus.spring.service;

import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswerPair;

/**
 * @author Александр Шабанов
 */
public interface AskQuestionService {
  QuestionAnswerPair proceedQuestion(Question question);
}
