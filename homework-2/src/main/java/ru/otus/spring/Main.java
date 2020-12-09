package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.QuestionService;
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);
        QuestionService service = context.getBean(QuestionService.class);
        service.run();
        context.close();
    }
}
