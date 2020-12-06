package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
public class UserInterfaceConfig {
    @Bean
    public PrintStream printStream(){
        return System.out;
    }
    @Bean
    public InputStream inputStream(){
        return System.in;
    }

}
