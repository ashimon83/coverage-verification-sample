package com.example.demo.application.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.domain.model.Book;
import com.example.demo.domain.model.ISBN;
import com.example.demo.domain.model.Price;
import com.example.demo.domain.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    private static final Long BOOK_ID = 1L;
    private static final String TITLE = "テスト駆動開発";
    private static final String AUTHOR = "Kent Beck";
    private static final String ISBN_VALUE = "978-4-274-21788-7";
    private static final BigDecimal PRICE_AMOUNT = new BigDecimal("3300");

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;
    private Book testBook;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository);
        testBook = new Book(BOOK_ID, TITLE, AUTHOR, new ISBN(ISBN_VALUE), new Price(PRICE_AMOUNT));
    }

    @Test
    void 本が正しく作成される() {
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        Book createdBook = bookService.createBook(TITLE, AUTHOR, ISBN_VALUE, PRICE_AMOUNT);

        assertAll(
            () -> assertEquals(TITLE, createdBook.getTitle()),
            () -> assertEquals(AUTHOR, createdBook.getAuthor()),
            () -> assertEquals(ISBN_VALUE, createdBook.getIsbn().getValue()),
            () -> assertEquals(PRICE_AMOUNT, createdBook.getPrice().getAmount())
        );
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void IDで本を検索できる() {
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(testBook));

        Optional<Book> found = bookService.getBook(BOOK_ID);

        assertTrue(found.isPresent());
        assertEquals(testBook, found.get());
        verify(bookRepository).findById(BOOK_ID);
    }

    @Test
    void 存在しない本をIDで検索すると空のOptionalが返される() {
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.empty());

        Optional<Book> found = bookService.getBook(BOOK_ID);

        assertFalse(found.isPresent());
        verify(bookRepository).findById(BOOK_ID);
    }

    @Test
    void すべての本を取得できる() {
        List<Book> books = Arrays.asList(
            testBook,
            new Book(2L, "リファクタリング", "Martin Fowler", new ISBN("978-4-274-22456-4"), new Price(new BigDecimal("4400")))
        );
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> found = bookService.getAllBooks();

        assertEquals(2, found.size());
        assertEquals(books, found);
        verify(bookRepository).findAll();
    }

    @Test
    void 著者名で本を検索できる() {
        List<Book> books = Arrays.asList(testBook);
        when(bookRepository.findByAuthor(AUTHOR)).thenReturn(books);

        List<Book> found = bookService.getBooksByAuthor(AUTHOR);

        assertEquals(1, found.size());
        assertEquals(books, found);
        verify(bookRepository).findByAuthor(AUTHOR);
    }

    @Test
    void 本が正しく更新される() {
        String newTitle = "新しいタイトル";
        String newAuthor = "新しい著者";
        BigDecimal newPrice = new BigDecimal("4400");

        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        Book updatedBook = bookService.updateBook(BOOK_ID, newTitle, newAuthor, newPrice);

        assertAll(
            () -> assertEquals(newTitle, updatedBook.getTitle()),
            () -> assertEquals(newAuthor, updatedBook.getAuthor()),
            () -> assertEquals(newPrice, updatedBook.getPrice().getAmount())
        );
        verify(bookRepository).findById(BOOK_ID);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void 存在しない本の更新時に例外がスローされる() {
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
            () -> bookService.updateBook(BOOK_ID, "新しいタイトル", "新しい著者", new BigDecimal("4400")));

        verify(bookRepository).findById(BOOK_ID);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void 本が正しく削除される() {
        bookService.deleteBook(BOOK_ID);
        verify(bookRepository).delete(BOOK_ID);
    }
} 