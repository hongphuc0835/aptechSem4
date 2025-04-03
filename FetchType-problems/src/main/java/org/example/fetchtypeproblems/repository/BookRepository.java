package org.example.fetchtypeproblems.repository;

import jakarta.persistence.Entity;
import org.example.fetchtypeproblems.model.Author;
import org.example.fetchtypeproblems.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
//    Cach 1: dung JPQL
    @Query("select b from Book b join b.authors where b.id = :id")
    Optional<Book> findBookWithAuthors(@Param("id") Long id);

//    Cach 2: EntityGraph
    @EntityGraph(attributePaths = "authors")
    Optional<Book> findById(@Param("id") Long id);

    @EntityGraph(attributePaths = "authors")
    List<Book> findAll();

      // Sử dụng EntityGraph để load authors và category
    @EntityGraph(attributePaths = {"authors", "category"})
    Optional<Book> findWithDetailById(@Param("id") Long id);


    // JPQL JOIN FETCH để tối ưu
    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors LEFT JOIN FETCH b.category WHERE b.id = :id")
    Optional<Book> findByIdWithAuthorsAndCategory(@Param("id") Long id);

    // Tìm sách theo category với BatchSize
    List<Book> findByCategoryId(Long categoryId);
}


