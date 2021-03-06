package ru.otus.spring.dto;

import lombok.Data;

/**
 * @author Александр Шабанов
 */
@Data
public class CommentDto {
    private String id;
    private UserDto user;
    private String commentText;
    private BookDto book;
}

