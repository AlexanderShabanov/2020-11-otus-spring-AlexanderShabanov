package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Score {
    private final int mark;
    private final boolean  isTestCompleted;
}
