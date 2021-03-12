package ru.otus.spring;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@EnableMongock
@SpringBootApplication
public class SpringHomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringHomeworkApplication.class, args);

    }

}
