package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.models.Author;
import ru.otus.spring.repositories.AuthorRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorRepository authorRepository;

    @GetMapping("/api/v2/author")
    public Flux<Author> listAuthors() {
        return authorRepository.findAll();
    }

}
