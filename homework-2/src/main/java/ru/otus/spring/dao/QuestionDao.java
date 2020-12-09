package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.util.Collection;

/**
 * @author Александр Шабанов
 */
public interface QuestionDao {
  Collection<Question> getQuestionCollection();
}
