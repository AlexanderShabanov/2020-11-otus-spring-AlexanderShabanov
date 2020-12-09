package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.dao.QuestionDaoImpl;
import ru.otus.spring.service.LocalizedMessageSource;

/**
 * @author Александр Шабанов
 */
@Configuration
public class QuestionDaoConfig {
  @Bean
  public QuestionDao questionDao(LocalizedMessageSource messageSource){
    String resourceName = messageSource.getMessage("dao.resourceName");
    return new QuestionDaoImpl(resourceName);
  }
}
