package ru.otus.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Александр Шабанов
 */

@ConfigurationProperties("questions")
@RequiredArgsConstructor
public class QuestionDaoProperties {
  private final CommonPropertiesConfig commonPropertiesConfig;
  private String baseName;

  public String getBaseName() {
    return baseName.replaceFirst("<locale>", commonPropertiesConfig.getLocale().toString());
  }

  public void setBaseName(String baseName) {
    this.baseName = baseName;
  }
}
