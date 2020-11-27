package ru.otus.spring.service;

import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.QuestionAnswerPair;

import java.util.Collection;

/**
 * @author Александр Шабанов
 */
public class AnswerScoringImpl implements AnswerScoring {
  private final static long RIGHT_ANSWER_MARK = 10;
  private final static long WRONG_ANSWER_MARK = -20;

  private long overallMark = 0;

  @Override
  public long getScore(Collection<QuestionAnswerPair> questionAnswerPairCollection) {
    questionAnswerPairCollection.forEach(this::getScoreByQuestionAnswerPair);
    return overallMark;
  }

  private void getScoreByQuestionAnswerPair(QuestionAnswerPair questionAnswerPair) {
    long rightAnswersNumberGotten = questionAnswerPair.getQuestion().getAnswerCollection().stream().filter(Answer::isAnswerValid).filter(answer -> questionAnswerPair.getRealAnswerCollection().stream().anyMatch(answer1 -> answer1.getAnswerText().equalsIgnoreCase(answer.getAnswerText()))).count();
    long rightAnswersNumberExpected = questionAnswerPair.getQuestion().getAnswerCollection().stream().filter(Answer::isAnswerValid).count();
    long wrongAnswersNumberGotten = questionAnswerPair.getRealAnswerCollection().size() - rightAnswersNumberGotten;
    overallMark += rightAnswersNumberGotten * RIGHT_ANSWER_MARK + wrongAnswersNumberGotten * WRONG_ANSWER_MARK;
  }
}
