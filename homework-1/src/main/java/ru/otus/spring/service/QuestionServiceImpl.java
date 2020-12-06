package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswerPair;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Александр Шабанов
 */
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
  private static final String IDENT = "    ";
  private final QuestionDao questionDao;
  private final AnswerScoring answerScoring;

  @Override
  /**
   * Точка входа из main
   */
  public long run() {
    Collection<Question> questionCollection = questionDao.getQuestionCollection();
    return answerScoring.getScore(proceedQuestions(questionCollection));
  }

  private Collection<QuestionAnswerPair> proceedQuestions(Collection<Question> questionCollection) {
    Collection<QuestionAnswerPair> questionAnswerPairs = new ArrayList<>();
    questionCollection.forEach(question -> questionAnswerPairs.add(proceedQuestion(question)));
    return questionAnswerPairs;
  }

  /**
   * Выводим вопрос и варианты ответов + в перспективе ожидаем ответ и сохраняем
   * @param question вопрос из файла
   */
  private QuestionAnswerPair proceedQuestion(Question question) {
    System.out.println(question.getQuestionText());
    if(!question.getAnswerCollection().isEmpty()){
      System.out.println(String.format("%s Варианты ответов:", IDENT));
      for(Answer answer:question.getAnswerCollection()){
        System.out.println(String.format("%s %s", IDENT, answer.getAnswerText()));
      }
    }
    return new QuestionAnswerPair(question,getAnswers());
  }

  private Collection<Answer> getAnswers() {
    //Unimplemented yet
    return new ArrayList<>();
  }


}
