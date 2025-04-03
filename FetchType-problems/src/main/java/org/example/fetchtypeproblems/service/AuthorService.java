package org.example.fetchtypeproblems.service;

import org.example.fetchtypeproblems.model.Author;
import org.example.fetchtypeproblems.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public Author getAuthorWithBooks(Long id) {
        return authorRepository.findWithBooksById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @Transactional
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }
}