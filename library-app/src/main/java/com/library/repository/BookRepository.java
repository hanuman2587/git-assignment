package com.library.repository;

import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Find by title (case-insensitive)
    Optional<Book> findByTitleIgnoreCase(String title);

    // Find by genre
    List<Book> findByGenre(String genre);

    // Find all books by a specific author id
    List<Book> findByAuthorId(Long authorId);

    /**
     * Custom JPQL inner join query: fetches book and author info together.
     * This performs an INNER JOIN between books and authors tables.
     */
    @Query("SELECT new com.library.entity.BookAuthorDTO(" +
           "b.id, b.title, b.genre, b.publishYear, b.isbn, " +
           "a.id, a.name, a.nationality) " +
           "FROM Book b INNER JOIN b.author a " +
           "ORDER BY a.name, b.title")
    List<BookAuthorDTO> findAllBooksWithAuthorDetails();

    /**
     * Custom JPQL inner join filtered by genre.
     */
    @Query("SELECT new com.library.entity.BookAuthorDTO(" +
           "b.id, b.title, b.genre, b.publishYear, b.isbn, " +
           "a.id, a.name, a.nationality) " +
           "FROM Book b INNER JOIN b.author a " +
           "WHERE b.genre = :genre ORDER BY b.title")
    List<BookAuthorDTO> findBooksWithAuthorByGenre(@Param("genre") String genre);
}
