package ru.otus.spring.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author Александр Шабанов
 * Вариант ответа на вопрос
 */

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Answer {
  private final String answerText;
  private final boolean answerValid;
}
