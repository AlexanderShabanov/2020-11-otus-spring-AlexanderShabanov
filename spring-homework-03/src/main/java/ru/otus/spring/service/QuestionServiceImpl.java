package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswerPair;
import ru.otus.spring.domain.Score;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * @author Александр Шабанов
 */
@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {
  private static final String IDENT = "    ";
  private final QuestionDao questionDao;
  private final AnswerScoring answerScoring;
  private final UserInterfaceService userInterfaceService;
  private final MessageSource messageSource;

  @Override
  /**
   * Точка входа из main
   */
  public Score run() {
    Collection<Question> questionCollection = questionDao.getQuestionCollection();
    Score score = answerScoring.getScore(proceedQuestions(questionCollection));
    userInterfaceService.textOut(String.format("Вы набрали %s баллов. Тест %s",score.getMark(), score.isTestCompleted()?" пройден!" : "не пройден!"));
    return score;
  }

  private Collection<QuestionAnswerPair> proceedQuestions(Collection<Question> questionCollection) {
    Collection<QuestionAnswerPair> questionAnswerPairs = new ArrayList<>();
    questionCollection.forEach(question -> questionAnswerPairs.add(proceedQuestion(question)));
    return questionAnswerPairs;
  }

  /**
   * Выводим вопрос и варианты ответов +  ожидаем ответ и сохраняем
   * @param question вопрос из файла
   */
  private QuestionAnswerPair proceedQuestion(Question question) {
    userInterfaceService.textOut("Вопросы могут иметь несколько правильных ответов. Значения при ответе разделяются запятой.");
    userInterfaceService.textOut(question.getQuestionText());
    if(!question.getAnswerCollection().isEmpty()){
      userInterfaceService.textOut(String.format("%s Варианты ответов:", IDENT));
      for(Answer answer:question.getAnswerCollection()){
        userInterfaceService.textOut(String.format("%s %s", IDENT, answer.getAnswerText()));
      }
    }
    return new QuestionAnswerPair(question,getAnswers());
  }

  private Collection<Answer> getAnswers() {
    Collection<Answer> answerCollection = new ArrayList<>();
    String answerString = userInterfaceService.textIn();
    Scanner answerScanner = new Scanner(answerString).useDelimiter(",");
    while(answerScanner.hasNext()) {
      answerCollection.add(new Answer(answerScanner.next(),false));
    }
    return answerCollection;
  }


}
