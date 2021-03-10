package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/api/v2/book")
    public Flux<Book> listPage() {
        return bookRepository.findAll();//.delayElements(Duration.ofMillis(100L));
    }

    @GetMapping("/api/v2/book/{id}")
    public Mono<Book> viewBook(@PathVariable("id") String id) {
        return bookRepository.findById(id);
    }

    @PostMapping("/api/v2/book")
    public Mono<Book> addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/api/v2/book")
    public Mono<Book> updateBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/api/v2/book/{id}")
    public void deleteBook(@PathVariable String id) {
        bookRepository.deleteById(id);
    }

    @GetMapping("/api/v2/book/{id}/comment")
    public Flux<Comment> getComments(@PathVariable("id") String id) {
        return commentRepository.findByBookId(id);
    }
}
