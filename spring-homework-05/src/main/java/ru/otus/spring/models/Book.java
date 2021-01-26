package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Александр Шабанов
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {
  private long id;
  private Author author;
  private Genre genre;
  private String name;
  public Map<String, Object> toMap(){
    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("name", name);
    map.put("author_id", author.getId());
    map.put("genre_id", genre.getId());
    return map;
  }
}
