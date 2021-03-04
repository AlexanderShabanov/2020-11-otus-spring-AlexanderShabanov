package ru.otus.spring.service.conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import ru.otus.spring.dto.AuthorDto;

import java.text.ParseException;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class AuthorFormatter implements Formatter<AuthorDto> {
    private final ObjectMapper mapper;

    @Override
    public AuthorDto parse(String text, Locale locale) throws ParseException {
        AuthorDto authorDto;
        try {
            authorDto = mapper.readValue(text, AuthorDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            authorDto = new AuthorDto();
        }
        return authorDto;
    }

    @Override
    public String print(AuthorDto object, Locale locale) {
        String result;
        try {
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }
}
