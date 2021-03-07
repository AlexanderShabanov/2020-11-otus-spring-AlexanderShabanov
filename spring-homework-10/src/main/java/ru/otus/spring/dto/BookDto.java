package ru.otus.spring.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Александр Шабанов
 */
@Data
public class BookDto {
    private String id;
    private AuthorDto author;
    private List<GenreDto> genre;
    private String name;

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", author=" + author +
                ", genre=" + genre +
                ", name='" + name + '\'' +
                '}';
    }
}
