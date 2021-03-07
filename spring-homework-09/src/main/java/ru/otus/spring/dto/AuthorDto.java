package ru.otus.spring.dto;

import lombok.Data;

/**
 * @author Александр Шабанов
 */
@Data
public class AuthorDto {
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
