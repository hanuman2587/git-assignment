package com.library.repository;

import com.library.entity.Author;
import com.library.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        author1 = authorRepository.save(new Author("George Orwell", "British", 1903));
        author2 = authorRepository.save(new Author("J.K. Rowling", "British", 1965));

        // author2 has a book; author1 does not
        bookRepository.save(new Book("Harry Potter", "Fantasy", 1997, "978-0000000001", author2));
    }

    @Test
    void testFindAll_ReturnsTwoAuthors() {
        List<Author> all = authorRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    void testFindByNameIgnoreCase_Found() {
        Optional<Author> found = authorRepository.findByNameIgnoreCase("george orwell");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("George Orwell");
    }

    @Test
    void testFindByNameIgnoreCase_NotFound() {
        Optional<Author> found = authorRepository.findByNameIgnoreCase("Unknown Author");
        assertThat(found).isEmpty();
    }

    @Test
    void testFindByNationality() {
        List<Author> british = authorRepository.findByNationality("British");
        assertThat(british).hasSize(2);
    }

    @Test
    void testFindAuthorsWithBooks_OnlyReturnsAuthorWithBook() {
        List<Author> withBooks = authorRepository.findAuthorsWithBooks();
        assertThat(withBooks).hasSize(1);
        assertThat(withBooks.get(0).getName()).isEqualTo("J.K. Rowling");
    }

    @Test
    void testSaveAndFindById() {
        Author saved = authorRepository.save(new Author("Toni Morrison", "American", 1931));
        Optional<Author> found = authorRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getNationality()).isEqualTo("American");
    }

    @Test
    void testUpdateAuthor() {
        author1.setNationality("English");
        Author updated = authorRepository.save(author1);
        assertThat(updated.getNationality()).isEqualTo("English");
    }
}
