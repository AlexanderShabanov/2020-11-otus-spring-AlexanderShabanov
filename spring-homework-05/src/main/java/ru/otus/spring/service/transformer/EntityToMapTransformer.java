package ru.otus.spring.service.transformer;

import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.util.Map;

public interface EntityToMapTransformer {
    Map<String, Object> toMap(Author author);

    Map<String, Object> toMap(Book book);

    Map<String, Object> toMap(Genre genre);
}
