package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.otus.spring.config")
public class SpringHomeworkApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringHomeworkApplication.class, args);

  }

}
