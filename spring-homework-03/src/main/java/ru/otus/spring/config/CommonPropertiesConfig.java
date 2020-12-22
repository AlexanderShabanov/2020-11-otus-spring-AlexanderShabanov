package ru.otus.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

/**
 * @author Александр Шабанов
 */
@Data
@ConfigurationProperties(prefix = "common")
public class CommonPropertiesConfig {
  private Locale locale;
}
