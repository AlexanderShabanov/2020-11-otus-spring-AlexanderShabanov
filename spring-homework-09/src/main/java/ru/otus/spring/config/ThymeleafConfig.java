package ru.otus.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.otus.spring.service.conversion.AuthorFormatter;
import ru.otus.spring.service.conversion.GenreFormatter;

@Configuration
//@EnableWebMvc
@ComponentScan
@RequiredArgsConstructor
public class ThymeleafConfig extends WebMvcConfigurerAdapter {

    private final AuthorFormatter authorFormatter;
    private final GenreFormatter genreFormatter;

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addFormatter(authorFormatter);
        registry.addFormatter(genreFormatter);
    }
}