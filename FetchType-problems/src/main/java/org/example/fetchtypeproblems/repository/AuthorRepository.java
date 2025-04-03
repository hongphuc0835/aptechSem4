package org.example.fetchtypeproblems.repository;

import org.example.fetchtypeproblems.model.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Sử dụng @EntityGraph để load books của author
    @EntityGraph(attributePaths = {"books"})
    Optional<Author> findWithBooksById(Long id);

    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books WHERE a.id = :id")
    Optional<Author> findByIdWithBooks(@Param("id") Long id);
}