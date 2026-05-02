package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Populates the database with 10 Authors and 10 Books on application startup.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public DataInitializer(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        if (authorRepository.count() > 0) return; // Already seeded

        // --- 10 Authors ---
        Author a1  = authorRepository.save(new Author("George Orwell",       "British",    1903));
        Author a2  = authorRepository.save(new Author("J.K. Rowling",        "British",    1965));
        Author a3  = authorRepository.save(new Author("Haruki Murakami",     "Japanese",   1949));
        Author a4  = authorRepository.save(new Author("Gabriel García Márquez", "Colombian", 1927));
        Author a5  = authorRepository.save(new Author("Toni Morrison",       "American",   1931));
        Author a6  = authorRepository.save(new Author("Franz Kafka",         "Czech",      1883));
        Author a7  = authorRepository.save(new Author("Virginia Woolf",      "British",    1882));
        Author a8  = authorRepository.save(new Author("Leo Tolstoy",         "Russian",    1828));
        Author a9  = authorRepository.save(new Author("Chimamanda Adichie",  "Nigerian",   1977));
        Author a10 = authorRepository.save(new Author("Fyodor Dostoevsky",   "Russian",    1821));

        // --- 10 Books ---
        bookRepository.saveAll(List.of(
            new Book("1984",                        "Dystopian",      1949, "978-0451524935", a1),
            new Book("Animal Farm",                 "Political",      1945, "978-0451526342", a1),
            new Book("Harry Potter and the Sorcerer's Stone", "Fantasy", 1997, "978-0439708180", a2),
            new Book("Norwegian Wood",              "Literary Fiction", 1987, "978-0375704024", a3),
            new Book("One Hundred Years of Solitude", "Magical Realism", 1967, "978-0060883287", a4),
            new Book("Beloved",                     "Historical Fiction", 1987, "978-1400033416", a5),
            new Book("The Metamorphosis",           "Absurdist Fiction", 1915, "978-0553213690", a6),
            new Book("Mrs Dalloway",                "Modernist",      1925, "978-0156628709", a7),
            new Book("War and Peace",               "Historical Fiction", 1869, "978-1400079988", a8),
            new Book("Purple Hibiscus",             "Literary Fiction", 2003, "978-1616953638", a9)
        ));

        System.out.println("=== Database seeded: 10 authors, 10 books ===");
    }
}
