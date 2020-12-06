package ru.otus.spring.service;

import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.QuestionAnswerPair;

import java.util.*;

/**
 * @author Александр Шабанов
 */
public class AnswerScoringImpl implements AnswerScoring {
  private final static int RIGHT_ANSWER_MARK = 10;
  private final static int WRONG_ANSWER_MARK = -20;


  @Override
  public int getScore(Collection<QuestionAnswerPair> questionAnswerPairCollection) {
    int overallMark = 0;
    for(QuestionAnswerPair questionAnswerPair : questionAnswerPairCollection){
      overallMark += getScoreByQuestionAnswerPair(questionAnswerPair);
    }
    return overallMark;
  }

  private int getScoreByQuestionAnswerPair(QuestionAnswerPair questionAnswerPair) {
    int rightAnswersNumberGotten = (int) questionAnswerPair.getQuestion().getAnswerCollection().stream().filter(Answer::isAnswerValid).filter(answer -> questionAnswerPair.getRealAnswerCollection().stream().anyMatch(answer1 -> answer1.getAnswerText().equalsIgnoreCase(answer.getAnswerText()))).count();
    int rightAnswersNumberExpected = (int) questionAnswerPair.getQuestion().getAnswerCollection().stream().filter(Answer::isAnswerValid).count();
    int wrongAnswersNumberGotten = questionAnswerPair.getRealAnswerCollection().size() - rightAnswersNumberGotten;
    return (rightAnswersNumberGotten * RIGHT_ANSWER_MARK + wrongAnswersNumberGotten * WRONG_ANSWER_MARK);
  }
}
