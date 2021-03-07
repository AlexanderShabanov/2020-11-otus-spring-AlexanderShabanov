package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.LibraryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final LibraryService libraryService;

    @GetMapping("/author")
    public List<AuthorDto> listAuthors() {
        List<AuthorDto> authors = libraryService.findAllAuthors();
        return authors;
    }

    @GetMapping("/genre")
    public List<GenreDto> listGenres() {
        List<GenreDto> genres = libraryService.findAllGenres();
        return genres;
    }

    @GetMapping("/book")
    public List<BookDto> listPage() {
        List<BookDto> books = libraryService.findAllBooks();
        return books;
    }

    @GetMapping("/book/{id}") //по идее модель общая для редактирования и удаления (нужно подтверждение), а формы разные
    public BookDto viewBook(@PathVariable("id") String id) {
        BookDto book = libraryService.findBookById(id);
        return book;
    }


    @PostMapping("/book")
    public BookDto addBook(@RequestBody BookDto book) {
        return libraryService.insertBook(book, false);
    }

    @PutMapping("/book")
    public BookDto updateBook(@RequestBody BookDto book) {

        return libraryService.updateBook(book, false);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable String id) {
        libraryService.deleteBook(id);
    }

    @GetMapping("/book/{id}/comment")
    //по идее модель общая для редактирования и удаления (нужно подтверждение), а формы разные
    public List<CommentDto> getComments(@PathVariable("id") String id) {
        List<CommentDto> commentDto = libraryService.findAllCommentsByBookId(id);
        return commentDto;
    }
}
