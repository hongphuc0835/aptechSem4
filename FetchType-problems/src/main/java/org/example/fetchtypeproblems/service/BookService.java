package org.example.fetchtypeproblems.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.fetchtypeproblems.model.Book;
import org.example.fetchtypeproblems.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }
//    @Transactional(readOnly = true)
//    public Book getBookById(Long id) {
////    return bookRepository.findById(id).orElseThrow();
//    Book book = bookRepository.findById(id).orElseThrow();
//    book.getAuthors().size();
//    return book;
//    }

//    @Transactional(readOnly = true)
//    public Book getBookById(Long id) {
//        return bookRepository.findWithDetailById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
//    }


    @Transactional(readOnly = true) // Giữ session mở để LAZY hoạt động
    public Book getBookDetails(Long id) {
        return bookRepository.findByIdWithAuthorsAndCategory(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByCategory(Long categoryId) {
        return bookRepository.findByCategoryId(categoryId); // Sử dụng BatchSize
    }

    @Transactional
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }


}

