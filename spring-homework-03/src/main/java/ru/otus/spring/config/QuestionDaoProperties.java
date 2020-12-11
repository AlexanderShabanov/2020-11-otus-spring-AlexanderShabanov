package ru.otus.spring.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Александр Шабанов
 */
@NoArgsConstructor
@Data
@ConfigurationProperties("questions")
public class QuestionDaoProperties {
  private String baseName;
}
