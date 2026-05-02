package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    private Author author;
    private Book book;

    @BeforeEach
    void setUp() {
        author = new Author("George Orwell", "British", 1903);
        author.setId(1L);

        book = new Book("1984", "Dystopian", 1949, "978-0000000001", author);
        book.setId(1L);
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        List<Book> books = bookService.getAllBooks();
        assertThat(books).hasSize(1);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById_Found() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Optional<Book> result = bookService.getBookById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("1984");
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Book> result = bookService.getBookById(99L);
        assertThat(result).isEmpty();
    }

    @Test
    void testSaveBook_Success() {
        when(bookRepository.findAll()).thenReturn(List.of());
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book saved = bookService.saveBook(book);
        assertThat(saved.getTitle()).isEqualTo("1984");
        verify(bookRepository).save(book);
    }

    @Test
    void testSaveBook_DuplicateIsbn_ThrowsException() {
        Book duplicate = new Book("Duplicate", "Fiction", 2020, "978-0000000001", author);
        duplicate.setId(2L);

        when(bookRepository.findAll()).thenReturn(List.of(book));
        assertThrows(DataIntegrityViolationException.class, () -> bookService.saveBook(duplicate));
    }

    @Test
    void testUpdateBook_Success() {
        Book updated = new Book("Nineteen Eighty-Four", "Dystopian", 1949, "978-0000000001", author);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book result = bookService.updateBook(1L, updated, 1L);
        assertThat(result.getTitle()).isEqualTo("Nineteen Eighty-Four");
    }

    @Test
    void testUpdateBook_BookNotFound_ThrowsException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> bookService.updateBook(99L, book, 1L));
    }

    @Test
    void testGetAllBooksWithAuthorDetails() {
        BookAuthorDTO dto = new BookAuthorDTO(1L, "1984", "Dystopian", 1949,
                "978-0000000001", 1L, "George Orwell", "British");
        when(bookRepository.findAllBooksWithAuthorDetails()).thenReturn(List.of(dto));

        List<BookAuthorDTO> result = bookService.getAllBooksWithAuthorDetails();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAuthorName()).isEqualTo("George Orwell");
    }
}
