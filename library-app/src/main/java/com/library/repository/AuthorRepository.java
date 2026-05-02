package com.library.repository;

import com.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Find by name (case-insensitive)
    Optional<Author> findByNameIgnoreCase(String name);

    // Find all authors from a given nationality
    List<Author> findByNationality(String nationality);

    // Custom JPQL: authors who have at least one book
    @Query("SELECT DISTINCT a FROM Author a JOIN a.books b")
    List<Author> findAuthorsWithBooks();
}
