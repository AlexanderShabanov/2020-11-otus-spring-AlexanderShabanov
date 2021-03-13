package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreRepository genreRepository;

    @GetMapping(path = "/api/v2/genre")
    public Flux<Genre> listGenres() {
        return genreRepository.findAll();
    }

}
