package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import ru.otus.spring.service.QuestionService;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.otus.spring.config")
public class SpringHomeworkApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(SpringHomeworkApplication.class, args);
		QuestionService service = (QuestionService) context.getBean("questionServiceImpl");
		service.run();
	}

}
