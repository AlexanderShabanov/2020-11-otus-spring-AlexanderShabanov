package ru.otus.spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

/**
 * @author Александр Шабанов
 */
@RequiredArgsConstructor
@Getter
public class QuestionAnswerPair {
  private final Question question;
  private final Collection<Answer> realAnswerCollection;
}
