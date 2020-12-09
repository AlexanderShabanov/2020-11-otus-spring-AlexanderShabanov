package ru.otus.spring.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;

/**
 * @author Александр Шабанов
 * Вопрос с допустимыми вариантами ответа
 */
@EqualsAndHashCode
@Getter
public class Question {

    private final String questionText;
    private final Collection<Answer> answerCollection;

    public Question(String questionText, Collection<Answer> answerCollection) {
        this.questionText = questionText;
        this.answerCollection = answerCollection;
    }
}
