package ru.otus.spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Александр Шабанов
 */
@Data
@NoArgsConstructor
public class AuthorDto implements Serializable {
    private String id;
    private String name;
    private String surName;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name +
                ", surName='" + surName + '\'' +
                '}';
    }
}
