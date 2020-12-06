package ru.otus.spring.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
//переделать на @ConfigurationProperties в задании по springBoot
public class AnswerScoringConfig {
    private final int rightAnswerMark;
    private final int wrongAnswerMark;
    private final int minimumMarkToCompleteTest;
    public AnswerScoringConfig(@Value("${answerScoring.rightAnswerMark}") int rightAnswerMark, @Value("${answerScoring.wrongAnswerMark}") int wrongAnswerMark, @Value("${answerScoring.minimumMarkToCompleteTest}") int minimumMarkToCompleteTest) {
        this.rightAnswerMark = rightAnswerMark;
        this.wrongAnswerMark = wrongAnswerMark;
        this.minimumMarkToCompleteTest = minimumMarkToCompleteTest;
    }
}
