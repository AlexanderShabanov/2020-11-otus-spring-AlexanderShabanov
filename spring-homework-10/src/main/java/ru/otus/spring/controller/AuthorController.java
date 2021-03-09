package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.service.LibraryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final LibraryService libraryService;

    @GetMapping("/api/v1/author")
    public List<AuthorDto> listAuthors() {
        List<AuthorDto> authors = libraryService.findAllAuthors();
        return authors;
    }

}
