package ru.otus.spring.service.transformer;

import org.springframework.stereotype.Component;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.util.HashMap;
import java.util.Map;

@Component
public class EntityToMapTransformerImpl implements EntityToMapTransformer {
    @Override
    public Map<String, Object> toMap(Author author) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", author.getId());
        map.put("name", author.getName());
        map.put("surName", author.getSurName());
        return map;
    }

    @Override
    public Map<String, Object> toMap(Book book) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", book.getId());
        map.put("name", book.getName());
        map.put("author_id", book.getAuthor().getId());
        map.put("genre_id", book.getGenre().getId());
        return map;
    }

    @Override
    public Map<String, Object> toMap(Genre genre) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", genre.getId());
        map.put("name", genre.getName());
        return map;
    }
}
