package ru.otus.spring.models;

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

}
