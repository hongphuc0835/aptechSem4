package org.example.springbootiocdibeantransactionalorm.repository;

import org.example.springbootiocdibeantransactionalorm.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
