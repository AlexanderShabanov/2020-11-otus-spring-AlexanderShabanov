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
  private final Collection<QuestionAnswerPair> questionAnswerPairs = new ArrayList<>();

  @Override
  /**
   * Точка входа из main
   */
  public long run() {
    Collection<Question> questionCollection = questionDao.getQuestionCollection();
    questionCollection.forEach(this::proceedQuestion);

    return answerScoring.getScore(questionAnswerPairs);
  }

  /**
   * Выводим вопрос и варианты ответов + в перспективе ожидаем ответ и сохраняем
   * @param question вопрос из файла
   */
  private void proceedQuestion(Question question) {
    StringBuilder sb = new StringBuilder();
    System.out.println(question.getQuestionText());
    if(!question.getAnswerCollection().isEmpty()){
      System.out.println(sb.append(IDENT).append("Варианты ответов:").toString());
      for(Answer answer:question.getAnswerCollection()){
        sb.setLength(0);
        System.out.println(sb.append(IDENT).append(answer.getAnswerText()).toString());
      }
      questionAnswerPairs.add(new QuestionAnswerPair(question,getAnswers()));
    }
  }

  private Collection<Answer> getAnswers() {
    //Unimplemented yet
    return new ArrayList<>();
  }


}
