package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.LibraryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final LibraryService libraryService;

    @GetMapping("/api/v1/genre")
    public List<GenreDto> listGenres() {
        List<GenreDto> genres = libraryService.findAllGenres();
        return genres;
    }
}
