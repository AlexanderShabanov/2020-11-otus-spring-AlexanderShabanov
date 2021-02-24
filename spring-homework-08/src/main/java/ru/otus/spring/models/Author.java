package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Александр Шабанов
 */
@Data
@Document
@AllArgsConstructor
@NoArgsConstructor

public class Author {
    @Id
    private String id;
    private String name;
    private String surName;
}
