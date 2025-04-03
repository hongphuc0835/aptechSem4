package org.example.fetchtypeproblems.controller;

import org.example.fetchtypeproblems.model.Book;
import org.example.fetchtypeproblems.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBookDetails(id);
    }
    @GetMapping("/id/{id}")
    public Book getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return book;
    }

    @GetMapping("/category/{categoryId}")
    public List<Book> getBooksByCategory(@PathVariable Long categoryId) {
        return bookService.getBooksByCategory(categoryId);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);

    }
}