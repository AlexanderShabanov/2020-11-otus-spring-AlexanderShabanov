package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * @author Александр Шабанов
 */
@Service
//@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao{
  private final String resourceName;

  public QuestionDaoImpl(@Value("${dao.resourceName}") String resourceName) {
    this.resourceName = resourceName;
  }

  @Override
  public Collection<Question> getQuestionCollection() {

    return getResource();
  }

  private Question parseLine(String line) {
    Scanner lineScanner = new Scanner(line);
    String questionText = "";
    List<Answer> answerList = new ArrayList<>();
    lineScanner.useDelimiter(";");
    if (lineScanner.hasNext()) {
      questionText = lineScanner.next();
      while (lineScanner.hasNext()) {
        String possibleAnswer = lineScanner.next();
        boolean isAnswerValid = false;
        if (lineScanner.hasNextBoolean()) {
          isAnswerValid = lineScanner.nextBoolean();
        }
        answerList.add(new Answer(possibleAnswer, isAnswerValid));
      }
    }
    lineScanner.close();
    return new Question(questionText, answerList);
  }

    private Collection<Question> getResource(){
      List<Question> questionList = new ArrayList<>();
      try (Scanner scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream(resourceName), StandardCharsets.UTF_8);){
        if(scanner.hasNextLine()){
          scanner.nextLine(); //первая строка - заголовки, пропускаем
        }
        while (scanner.hasNextLine()) {
          questionList.add(parseLine(scanner.nextLine()));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return questionList;
    }
  }