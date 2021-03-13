package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor

public class Comment {
    @Id
    private String id;
    //@DBRef а юзера сделаю для разнообразия embedded
    private User user;
    private String commentText;
    @ToString.Exclude
    @DBRef
    private Book book;
}
