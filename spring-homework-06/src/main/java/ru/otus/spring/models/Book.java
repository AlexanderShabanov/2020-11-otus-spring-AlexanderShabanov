package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * @author Александр Шабанов
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId", referencedColumnName = "id")
    private Author author;
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "bookToGenresLink", joinColumns = @JoinColumn(name = "bookId"),
    inverseJoinColumns = @JoinColumn(name = "genreId"))
    private List<Genre> genre;
    @Column(name = "name")
    private String name;
// проверял и вариант с двусторонней зависимостью, но решил, что коммент не является атрибутом книги.
// пока не удаляю коммент, несмотря на замечание сонара: вдруг при проверке ДЗ придется вернуть.
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
//    private List<Comment> comments;

}
