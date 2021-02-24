package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Александр Шабанов
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "author")

public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "surname")
  private String surName;
}
