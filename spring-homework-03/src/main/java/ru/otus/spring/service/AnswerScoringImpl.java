package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AnswerScoringConfig;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.QuestionAnswerPair;
import ru.otus.spring.domain.Score;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Александр Шабанов
 */
@Service
@RequiredArgsConstructor
public class AnswerScoringImpl implements AnswerScoring {

    private final AnswerScoringConfig answerScoringConfig;


    @Override
    public Score getScore(Collection<QuestionAnswerPair> questionAnswerPairCollection) {
        int overallMark = 0;
        for (QuestionAnswerPair questionAnswerPair : questionAnswerPairCollection) {
            overallMark += getScoreByQuestionAnswerPair(questionAnswerPair);
        }
        return new Score(overallMark, overallMark >= answerScoringConfig.getMinimumMarkToCompleteTest());
    }


    private int getScoreByQuestionAnswerPair(QuestionAnswerPair questionAnswerPair) {
        Collection<Answer> answerCollection = questionAnswerPair.getQuestion().getAnswerCollection();
        Set<Answer> rightAnswers = answerCollection.stream().filter(Answer::isAnswerValid).collect(Collectors.toSet());
        int rightAnswersNumberGotten = 0, wrongAnswersNumberGotten = 0;
//        int rightAnswersNumberExpected = rightAnswers.size();
        for (Answer realAnswer : questionAnswerPair.getRealAnswerCollection()) {
            if (rightAnswers.contains(realAnswer)) {
                rightAnswersNumberGotten++;
            } else {
                wrongAnswersNumberGotten++;
            }
        }

        return (rightAnswersNumberGotten * answerScoringConfig.getRightAnswerMark()
                + wrongAnswersNumberGotten * answerScoringConfig.getWrongAnswerMark()
        );
    }
}
