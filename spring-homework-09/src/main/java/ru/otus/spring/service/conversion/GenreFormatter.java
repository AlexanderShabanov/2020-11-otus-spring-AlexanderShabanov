package ru.otus.spring.service.conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import ru.otus.spring.dto.GenreDto;

import java.text.ParseException;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class GenreFormatter implements Formatter<GenreDto> {
    private final ObjectMapper mapper;

    @Override
    public GenreDto parse(String text, Locale locale) throws ParseException {
        GenreDto genreDto;
        try {
            genreDto = mapper.readValue(text, GenreDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            genreDto = new GenreDto();
        }
        return genreDto;
    }

    @Override
    public String print(GenreDto object, Locale locale) {
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
