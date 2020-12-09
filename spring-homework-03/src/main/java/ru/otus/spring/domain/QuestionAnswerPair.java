package ru.otus.spring.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

/**
 * @author Александр Шабанов
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class QuestionAnswerPair {
  private final Question question;
  private final Collection<Answer> realAnswerCollection;
}
