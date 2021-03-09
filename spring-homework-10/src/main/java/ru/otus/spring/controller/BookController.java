package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.service.LibraryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final LibraryService libraryService;

    @GetMapping("/api/v1/book")
    public List<BookDto> listPage() {
        List<BookDto> books = libraryService.findAllBooks();
        return books;
    }

    @GetMapping("/api/v1/book/{id}")
    public BookDto viewBook(@PathVariable("id") String id) {
        BookDto book = libraryService.findBookById(id);
        return book;
    }


    @PostMapping("/api/v1/book")
    public BookDto addBook(@RequestBody BookDto book) {
        return libraryService.insertBook(book, false);
    }

    @PutMapping("/api/v1/book")
    public BookDto updateBook(@RequestBody BookDto book) {

        return libraryService.updateBook(book, false);
    }

    @DeleteMapping("/api/v1/book/{id}")
    public void deleteBook(@PathVariable String id) {
        libraryService.deleteBook(id);
    }

    @GetMapping("/api/v1/book/{id}/comment")
    //по идее модель общая для редактирования и удаления (нужно подтверждение), а формы разные
    public List<CommentDto> getComments(@PathVariable("id") String id) {
        List<CommentDto> commentDto = libraryService.findAllCommentsByBookId(id);
        return commentDto;
    }
}
