package ru.otus.spring.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PagesController {

    @GetMapping("/")
    public String listPage(Model model) {
        model.addAttribute("keywords", "list users in Omsk, omsk, list users, list users free");
        return "list";
    }

    @GetMapping("/editBook")
    public String editBook(@RequestParam("id") String id, Model model) {
        model.addAttribute("bookId", id);
        return "edit";
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam("id") String id, Model model) {
        model.addAttribute("bookId", id);
        return "delete";
    }

    @GetMapping("/bookAdd")
    public String addBook(Model model) {
        return "add";
    }
}
