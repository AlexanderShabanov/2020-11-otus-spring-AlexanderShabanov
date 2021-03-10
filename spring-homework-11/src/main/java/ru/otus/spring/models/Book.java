package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author Александр Шабанов
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document


public class Book {
    @Id
    private String id;
    @DBRef
    private Author author;
    @DBRef
    private List<Genre> genre;
    private String name;

}
