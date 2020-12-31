package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.utils.IOContext;

@Configuration
public class UserInterfaceConfig {
    @Bean
    public IOContext ioContext(){
        return new IOContext(System.in, System.out);
    }

}
