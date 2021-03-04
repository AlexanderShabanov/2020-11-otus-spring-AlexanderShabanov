package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.controller.enums.BookViewModes;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.service.LibraryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final LibraryService libraryService;

    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> books = libraryService.findAllBooks();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/book") //по идее модель общая для редактирования и удаления (нужно подтверждение), а формы разные
    public String viewBook(@RequestParam("id") String id, @RequestParam("mode") BookViewModes mode, Model model) {
        BookDto book = libraryService.findBookById(id);
        model.addAttribute("book", book);
        if (mode.equals(BookViewModes.DELETE)) {
            return "delete";
        } else {
            return "edit";
        }
    }

    @GetMapping("/bookAdd")
    public String addBook(Model model) {
        BookDto book = new BookDto();
        model.addAttribute("book", book);

        return "add";
    }

    @PostMapping("/book")
    public String addBook(BookDto book, Model model) {
        libraryService.insertBook(book, false);
        return "redirect:/";
    }

    @PutMapping("/book")
    public String updateBook(BookDto book, Model model) {
        libraryService.updateBook(book, false);
        return "redirect:/";
    }

    @DeleteMapping("/book")
    public String deleteBook(BookDto book, Model model) {
        libraryService.deleteBook(book.getId());
        return "redirect:/";
    }

    @ModelAttribute
    public void allAuthors(Model model) {
        model.addAttribute("allAuthors", libraryService.findAllAuthors());
    }

    @ModelAttribute
    public void allGenres(Model model) {
        model.addAttribute("allGenres", libraryService.findAllGenres());
    }

}
