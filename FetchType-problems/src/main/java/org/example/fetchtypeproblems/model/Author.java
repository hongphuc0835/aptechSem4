package org.example.fetchtypeproblems.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY) // Mặc định LAZY
    @JsonIgnore
    private Set<Book> books = new HashSet<>();
}
