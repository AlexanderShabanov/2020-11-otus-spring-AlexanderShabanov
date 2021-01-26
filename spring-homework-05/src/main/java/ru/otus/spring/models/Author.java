package ru.otus.spring.models;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Александр Шабанов
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
  private long id;
  private String name;
  private String surName;

  public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("id", id);
    map.put("name", name);
    map.put("surName", surName);
    return map;
  }
}
