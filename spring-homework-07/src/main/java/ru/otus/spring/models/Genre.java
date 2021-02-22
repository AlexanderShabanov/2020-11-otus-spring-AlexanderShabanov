package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

/**
 * @author Александр Шабанов
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genre")
@BatchSize(size = 10)
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
}
