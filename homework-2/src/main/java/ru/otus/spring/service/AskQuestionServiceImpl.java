package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswerPair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * @author Александр Шабанов
 */
@Service
@RequiredArgsConstructor
public class AskQuestionServiceImpl implements AskQuestionService {
  private static final String IDENT = "    ";
  private final UserInterfaceService userInterfaceService;
  /**
   * Выводим вопрос и варианты ответов +  ожидаем ответ и сохраняем
   * @param question вопрос из файла
   */
  public QuestionAnswerPair proceedQuestion(Question question) {
    userInterfaceService.textOut("Вопросы могут иметь несколько правильных ответов. Значения при ответе разделяются запятой.");
    userInterfaceService.textOut(question.getQuestionText());
    if(!question.getAnswerCollection().isEmpty()){
      userInterfaceService.textOut(String.format("%s Варианты ответов:", IDENT));
      for(Answer answer:question.getAnswerCollection()){
        userInterfaceService.textOut("%s %s", IDENT, answer.getAnswerText());
      }
    }
    return new QuestionAnswerPair(question,getAnswers());
  }

  private Collection<Answer> getAnswers() {
    Collection<Answer> answerCollection = new ArrayList<>();
    String answerString = userInterfaceService.textIn();
    try(Scanner answerScanner = new Scanner(answerString).useDelimiter(",")) {
      while (answerScanner.hasNext()) {
        answerCollection.add(new Answer(answerScanner.next(), false));
      }
    }
    return answerCollection;
  }

}
