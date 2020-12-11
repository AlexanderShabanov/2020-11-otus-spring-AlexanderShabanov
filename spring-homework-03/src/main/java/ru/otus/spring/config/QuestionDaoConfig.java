package ru.otus.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.dao.QuestionDaoImpl;
import ru.otus.spring.service.LocalizedMessageSource;

/**
 * @author Александр Шабанов
 */
@Configuration
@RequiredArgsConstructor
public class QuestionDaoConfig {
  private final QuestionDaoProperties questionDaoProperties;
  private final CommonPropertiesConfig commonPropertiesConfig;
  @Bean
  public QuestionDao questionDao(){
    return new QuestionDaoImpl(parseFileName());
  }
  String parseFileName(){
    return questionDaoProperties.getBaseName().replaceFirst("<locale>", commonPropertiesConfig.getLocale().toString());
  }
}
