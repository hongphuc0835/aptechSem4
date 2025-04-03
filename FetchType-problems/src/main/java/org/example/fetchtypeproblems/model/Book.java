package org.example.fetchtypeproblems.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToMany(fetch = FetchType.LAZY) // Mặc định LAZY cho @ManyToMany
    @JoinTable(name = "book_authors")
    @JsonIgnore
    private Set<Author> authors = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY) // Chuyển từ EAGER sang LAZY
    @JoinColumn(name = "category_id")
    private Category category;

    // Constructor với các tham số
    public Book(String title, String description, Category category, Set<Author> authors) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.authors = authors;
    }

}