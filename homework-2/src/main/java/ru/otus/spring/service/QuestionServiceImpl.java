package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswerPair;
import ru.otus.spring.domain.Score;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Александр Шабанов
 */
@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

  private final QuestionDao questionDao;
  private final AnswerScoring answerScoring;
  private final AskQuestionService askQuestionService;
  private final UserInterfaceService userInterfaceService;

  @Override
  /**
   * Точка входа из main
   */
  public Score run() {
    Collection<Question> questionCollection = questionDao.getQuestionCollection();
    Score score = answerScoring.getScore(proceedQuestions(questionCollection));
    userInterfaceService.textOut("Вы набрали %s баллов. Тест %s",score.getMark(), score.isTestCompleted()?" пройден!" : " не пройден!");
    return score;
  }

  private Collection<QuestionAnswerPair> proceedQuestions(Collection<Question> questionCollection) {
    Collection<QuestionAnswerPair> questionAnswerPairs = new ArrayList<>();
    questionCollection.forEach(question -> questionAnswerPairs.add(askQuestionService.proceedQuestion(question)));
    return questionAnswerPairs;
  }




}
