package org.example.fetchtypeproblems.controller;

import org.example.fetchtypeproblems.model.Author;
import org.example.fetchtypeproblems.service.AuthorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public Author getAuthor(@PathVariable Long id) {
        return authorService.getAuthorWithBooks(id);
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }
}