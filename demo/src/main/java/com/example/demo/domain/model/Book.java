package com.example.demo.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Book {
    private final Long id;
    private String title;
    private String author;
    private ISBN isbn;
    private Price price;
    private final LocalDateTime createdAt;

    public Book(Long id, String title, String author, ISBN isbn, Price price) {
        this(id, title, author, isbn, price, LocalDateTime.now());
    }

    public Book(Long id, String title, String author, ISBN isbn, Price price, LocalDateTime createdAt) {
        this.validateTitle(title);
        this.validateAuthor(author);
        
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = Objects.requireNonNull(isbn);
        this.price = Objects.requireNonNull(price);
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("タイトルは必須です");
        }
        if (title.length() > 100) {
            throw new IllegalArgumentException("タイトルは100文字以内である必要があります");
        }
    }

    private void validateAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("著者名は必須です");
        }
        if (author.length() > 50) {
            throw new IllegalArgumentException("著者名は50文字以内である必要があります");
        }
    }

    public void updateTitle(String newTitle) {
        this.validateTitle(newTitle);
        this.title = newTitle;
    }

    public void updateAuthor(String newAuthor) {
        this.validateAuthor(newAuthor);
        this.author = newAuthor;
    }

    public void updatePrice(Price newPrice) {
        this.price = Objects.requireNonNull(newPrice);
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public ISBN getIsbn() { return isbn; }
    public Price getPrice() { return price; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
               Objects.equals(title, book.title) &&
               Objects.equals(author, book.author) &&
               Objects.equals(isbn, book.isbn) &&
               Objects.equals(price, book.price) &&
               Objects.equals(createdAt, book.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, isbn, price, createdAt);
    }
} 