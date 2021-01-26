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
public class Genre {
  private long id;
  private String name;

  public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("id", id);
    map.put("name", name);
    return map;
  }  
}
