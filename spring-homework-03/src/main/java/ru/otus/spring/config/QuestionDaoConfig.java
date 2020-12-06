package ru.otus.spring.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.dao.QuestionDaoImpl;

/**
 * @author Александр Шабанов
 */
@Configuration
public class QuestionDaoConfig {
  @Bean
  public QuestionDao questionDao(MessageSource messageSource, CommonPropertiesConfig commonPropertiesConfig){
    String resourceName = messageSource.getMessage("dao.resourceName", null, commonPropertiesConfig.getLocale());
    return new QuestionDaoImpl(resourceName);
  }
}
