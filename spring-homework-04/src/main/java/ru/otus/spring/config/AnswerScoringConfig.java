package ru.otus.spring.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "answer-scoring")
public class AnswerScoringConfig {
    private int rightAnswerMark;
    private int wrongAnswerMark;
    private int minimumMarkToCompleteTest;
}
