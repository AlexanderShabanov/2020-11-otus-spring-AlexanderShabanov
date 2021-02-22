package ru.otus.spring.dto;

import lombok.Data;

/**
 * @author Александр Шабанов
 */
@Data

public class AuthorDto {
  private Long id;
  private String name;
  private String surName;
}
