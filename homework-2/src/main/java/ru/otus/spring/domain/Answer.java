package ru.otus.spring.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * @author Александр Шабанов
 * Вариант ответа на вопрос
 */

@Getter
@RequiredArgsConstructor
public class Answer {
  private final String answerText;
  private final boolean answerValid;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Answer answer = (Answer) o;
    return Objects.equals(answerText, answer.answerText);
  }

  @Override
  public int hashCode() {
    return Objects.hash(answerText);
  }
}
