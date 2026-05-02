package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    /** Retrieve all books */
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /** Find book by ID */
    @Transactional(readOnly = true)
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Save a new book.
     * Throws DataIntegrityViolationException if ISBN already exists.
     */
    public Book saveBook(Book book) {
        // Check duplicate ISBN
        if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
            bookRepository.findAll().stream()
                .filter(b -> book.getIsbn().equals(b.getIsbn()) &&
                             !b.getId().equals(book.getId()))
                .findFirst()
                .ifPresent(b -> {
                    throw new DataIntegrityViolationException(
                        "A book with ISBN " + book.getIsbn() + " already exists.");
                });
        }
        return bookRepository.save(book);
    }

    /**
     * Update an existing book.
     */
    public Book updateBook(Long id, Book updatedBook, Long authorId) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));

        existing.setTitle(updatedBook.getTitle());
        existing.setGenre(updatedBook.getGenre());
        existing.setPublishYear(updatedBook.getPublishYear());
        existing.setIsbn(updatedBook.getIsbn());
        existing.setAuthor(author);
        return bookRepository.save(existing);
    }

    /** Delete a book */
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    /** Inner join: all books with author details */
    @Transactional(readOnly = true)
    public List<BookAuthorDTO> getAllBooksWithAuthorDetails() {
        return bookRepository.findAllBooksWithAuthorDetails();
    }

    /** Books by genre with author details */
    @Transactional(readOnly = true)
    public List<BookAuthorDTO> getBooksByGenreWithAuthor(String genre) {
        return bookRepository.findBooksWithAuthorByGenre(genre);
    }
}
