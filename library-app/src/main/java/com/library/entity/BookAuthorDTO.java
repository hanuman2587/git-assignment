package com.library.entity;

/**
 * DTO to hold the result of the inner join query between Book and Author.
 */
public class BookAuthorDTO {

    private Long bookId;
    private String bookTitle;
    private String genre;
    private int publishYear;
    private String isbn;
    private Long authorId;
    private String authorName;
    private String nationality;

    public BookAuthorDTO(Long bookId, String bookTitle, String genre, int publishYear,
                         String isbn, Long authorId, String authorName, String nationality) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.genre = genre;
        this.publishYear = publishYear;
        this.isbn = isbn;
        this.authorId = authorId;
        this.authorName = authorName;
        this.nationality = nationality;
    }

    public Long getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getGenre() { return genre; }
    public int getPublishYear() { return publishYear; }
    public String getIsbn() { return isbn; }
    public Long getAuthorId() { return authorId; }
    public String getAuthorName() { return authorName; }
    public String getNationality() { return nationality; }
}
