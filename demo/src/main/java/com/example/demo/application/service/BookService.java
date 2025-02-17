package com.example.demo.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Book;
import com.example.demo.domain.model.ISBN;
import com.example.demo.domain.model.Price;
import com.example.demo.domain.repository.BookRepository;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(String title, String author, String isbn, java.math.BigDecimal price) {
        Book book = new Book(
            null,
            title,
            author,
            new ISBN(isbn),
            new Price(price)
        );
        return bookRepository.save(book);
    }

    public Optional<Book> getBook(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public Book updateBook(Long id, String title, String author, java.math.BigDecimal price) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("指定された書籍が見つかりません"));

        if (title != null) {
            book.updateTitle(title);
        }
        if (author != null) {
            book.updateAuthor(author);
        }
        if (price != null) {
            book.updatePrice(new Price(price));
        }

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }
} 