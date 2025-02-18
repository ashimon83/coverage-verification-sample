package com.example.demo.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.demo.domain.model.Book;
import com.example.demo.domain.model.ISBN;
import com.example.demo.domain.model.Price;

class BookEntityTest {

    @Test
    void ドメインモデルからエンティティに変換できる() {
        Book book = new Book(
            1L,
            "テスト駆動開発",
            "Kent Beck",
            new ISBN("978-4-274-21788-7"),
            new Price(new BigDecimal("3300"))
        );

        BookEntity entity = BookEntity.fromDomain(book);

        assertEquals(1L, entity.getId());
        assertEquals("テスト駆動開発", entity.getTitle());
        assertEquals("Kent Beck", entity.getAuthor());
        assertEquals("978-4-274-21788-7", entity.getIsbn());
        assertEquals(new BigDecimal("3300"), entity.getPrice());
        assertEquals(book.getCreatedAt(), entity.getCreatedAt());
    }

    @Test
    void エンティティからドメインモデルに変換できる() {
        BookEntity entity = new BookEntity();
        entity.setId(1L);
        entity.setTitle("テスト駆動開発");
        entity.setAuthor("Kent Beck");
        entity.setIsbn("978-4-274-21788-7");
        entity.setPrice(new BigDecimal("3300"));
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);

        Book book = entity.toDomain();

        assertEquals(1L, book.getId());
        assertEquals("テスト駆動開発", book.getTitle());
        assertEquals("Kent Beck", book.getAuthor());
        assertEquals("978-4-274-21788-7", book.getIsbn().getValue());
        assertEquals(new BigDecimal("3300"), book.getPrice().getAmount());
        assertEquals(now, book.getCreatedAt());
    }
} 