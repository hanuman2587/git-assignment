package com.library.repository;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        author = authorRepository.save(new Author("George Orwell", "British", 1903));
        bookRepository.save(new Book("1984",         "Dystopian", 1949, "978-1111111111", author));
        bookRepository.save(new Book("Animal Farm",  "Political",  1945, "978-2222222222", author));
    }

    @Test
    void testFindAll_ReturnsTwoBooks() {
        assertThat(bookRepository.findAll()).hasSize(2);
    }

    @Test
    void testFindByTitleIgnoreCase() {
        assertThat(bookRepository.findByTitleIgnoreCase("1984")).isPresent();
        assertThat(bookRepository.findByTitleIgnoreCase("unknown")).isEmpty();
    }

    @Test
    void testFindByGenre() {
        List<Book> dystopian = bookRepository.findByGenre("Dystopian");
        assertThat(dystopian).hasSize(1);
        assertThat(dystopian.get(0).getTitle()).isEqualTo("1984");
    }

    @Test
    void testFindByAuthorId() {
        List<Book> books = bookRepository.findByAuthorId(author.getId());
        assertThat(books).hasSize(2);
    }

    @Test
    void testFindAllBooksWithAuthorDetails_InnerJoin() {
        List<BookAuthorDTO> results = bookRepository.findAllBooksWithAuthorDetails();
        assertThat(results).hasSize(2);
        results.forEach(dto -> {
            assertThat(dto.getAuthorName()).isEqualTo("George Orwell");
            assertThat(dto.getNationality()).isEqualTo("British");
        });
    }

    @Test
    void testFindBooksWithAuthorByGenre() {
        List<BookAuthorDTO> results = bookRepository.findBooksWithAuthorByGenre("Political");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getBookTitle()).isEqualTo("Animal Farm");
    }
}
