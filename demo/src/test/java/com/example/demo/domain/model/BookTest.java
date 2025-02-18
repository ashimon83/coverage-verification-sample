package com.example.demo.domain.model;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class BookTest {
    private static final Long ID = 1L;
    private static final String VALID_TITLE = "テスト駆動開発";
    private static final String VALID_AUTHOR = "Kent Beck";
    private static final ISBN VALID_ISBN = new ISBN("978-4-274-21788-7");
    private static final Price VALID_PRICE = new Price(new BigDecimal("3300"));

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(ID, VALID_TITLE, VALID_AUTHOR, VALID_ISBN, VALID_PRICE);
    }

    @Test
    void 有効な値でインスタンスが生成される() {
        assertAll(
            () -> assertEquals(ID, book.getId()),
            () -> assertEquals(VALID_TITLE, book.getTitle()),
            () -> assertEquals(VALID_AUTHOR, book.getAuthor()),
            () -> assertEquals(VALID_ISBN, book.getIsbn()),
            () -> assertEquals(VALID_PRICE, book.getPrice()),
            () -> assertNotNull(book.getCreatedAt())
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void タイトルが無効な場合に例外がスローされる(String invalidTitle) {
        assertThrows(IllegalArgumentException.class,
            () -> new Book(ID, invalidTitle, VALID_AUTHOR, VALID_ISBN, VALID_PRICE));
    }

    @Test
    void タイトルが長すぎる場合に例外がスローされる() {
        String longTitle = "a".repeat(101);
        assertThrows(IllegalArgumentException.class,
            () -> new Book(ID, longTitle, VALID_AUTHOR, VALID_ISBN, VALID_PRICE));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void 著者名が無効な場合に例外がスローされる(String invalidAuthor) {
        assertThrows(IllegalArgumentException.class,
            () -> new Book(ID, VALID_TITLE, invalidAuthor, VALID_ISBN, VALID_PRICE));
    }

    @Test
    void 著者名が長すぎる場合に例外がスローされる() {
        String longAuthor = "a".repeat(51);
        assertThrows(IllegalArgumentException.class,
            () -> new Book(ID, VALID_TITLE, longAuthor, VALID_ISBN, VALID_PRICE));
    }

    @Test
    void ISBNがnullの場合に例外がスローされる() {
        assertThrows(NullPointerException.class,
            () -> new Book(ID, VALID_TITLE, VALID_AUTHOR, null, VALID_PRICE));
    }

    @Test
    void 価格がnullの場合に例外がスローされる() {
        assertThrows(NullPointerException.class,
            () -> new Book(ID, VALID_TITLE, VALID_AUTHOR, VALID_ISBN, null));
    }

    @Test
    void タイトルが正しく更新される() {
        String newTitle = "リファクタリング";
        book.updateTitle(newTitle);
        assertEquals(newTitle, book.getTitle());
    }

    @Test
    void 著者名が正しく更新される() {
        String newAuthor = "Martin Fowler";
        book.updateAuthor(newAuthor);
        assertEquals(newAuthor, book.getAuthor());
    }

    @Test
    void 価格が正しく更新される() {
        Price newPrice = new Price(new BigDecimal("4400"));
        book.updatePrice(newPrice);
        assertEquals(newPrice, book.getPrice());
    }

    @Test
    void 価格更新時にnullを指定すると例外がスローされる() {
        assertThrows(NullPointerException.class, () -> book.updatePrice(null));
    }
} 