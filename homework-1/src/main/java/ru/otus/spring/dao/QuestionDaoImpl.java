package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao{
  private final String resourceName;
  private final List<Question> questionList = new ArrayList<>();
  @Override
  public Collection<Question> getQuestionCollection() {
    getResource();
    return questionList;
  }

  private void parseLine(String line) {
    Scanner lineScanner = new Scanner(line);
    lineScanner.useDelimiter(";");
    if (lineScanner.hasNext()) {
      String questionText = lineScanner.next();
      List<Answer> answerList = new ArrayList<>();
      while (lineScanner.hasNext()) {
        String possibleAnswer = lineScanner.next();
        boolean isAnswerValid = false;
        if (lineScanner.hasNextBoolean()) {
          isAnswerValid = lineScanner.nextBoolean();
        }
        answerList.add(new Answer(possibleAnswer, isAnswerValid));
      }
      questionList.add(new Question(questionText, answerList));
    }
  }
    private void getResource(){
      try {
        Scanner scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream(resourceName), StandardCharsets.UTF_8);
        if(scanner.hasNextLine()){
          scanner.nextLine(); //первая строка - заголовки, пропускаем
        }
        while (scanner.hasNextLine()) {
          parseLine(scanner.nextLine());
        }
        scanner.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }