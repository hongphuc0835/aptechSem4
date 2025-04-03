package org.example.fetchtypeproblems.repository;

import org.example.fetchtypeproblems.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
