package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("George Orwell", "British", 1903);
        author.setId(1L);
    }

    @Test
    void testGetAllAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(author));
        List<Author> result = authorService.getAllAuthors();
        assertThat(result).hasSize(1);
        verify(authorRepository).findAll();
    }

    @Test
    void testGetAuthorById_Found() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Optional<Author> result = authorService.getAuthorById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("George Orwell");
    }

    @Test
    void testGetAuthorById_NotFound() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThat(authorService.getAuthorById(99L)).isEmpty();
    }

    @Test
    void testSaveAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        Author saved = authorService.saveAuthor(author);
        assertThat(saved.getName()).isEqualTo("George Orwell");
        verify(authorRepository).save(author);
    }

    @Test
    void testUpdateAuthor_Success() {
        Author updated = new Author("Eric Blair", "British", 1903);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenAnswer(inv -> inv.getArgument(0));

        Author result = authorService.updateAuthor(1L, updated);
        assertThat(result.getName()).isEqualTo("Eric Blair");
    }

    @Test
    void testUpdateAuthor_NotFound_ThrowsException() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,
                () -> authorService.updateAuthor(99L, author));
    }

    @Test
    void testGetAuthorsWithBooks() {
        when(authorRepository.findAuthorsWithBooks()).thenReturn(List.of(author));
        List<Author> result = authorService.getAuthorsWithBooks();
        assertThat(result).hasSize(1);
    }
}
