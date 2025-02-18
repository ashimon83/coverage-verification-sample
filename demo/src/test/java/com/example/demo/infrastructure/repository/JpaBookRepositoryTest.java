package com.example.demo.infrastructure.repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.domain.model.Book;
import com.example.demo.domain.model.ISBN;
import com.example.demo.domain.model.Price;
import com.example.demo.infrastructure.entity.BookEntity;

@ExtendWith(MockitoExtension.class)
class JpaBookRepositoryTest {

    @Mock
    private SpringDataBookRepository springDataBookRepository;

    private JpaBookRepository jpaBookRepository;
    private Book testBook;
    private BookEntity testEntity;

    @BeforeEach
    void setUp() {
        jpaBookRepository = new JpaBookRepository(springDataBookRepository);
        
        testBook = new Book(
            1L,
            "テスト駆動開発",
            "Kent Beck",
            new ISBN("978-4-274-21788-7"),
            new Price(new BigDecimal("3300"))
        );
        
        testEntity = BookEntity.fromDomain(testBook);
    }

    @Test
    void 本が正しく保存される() {
        when(springDataBookRepository.save(any(BookEntity.class))).thenReturn(testEntity);

        Book savedBook = jpaBookRepository.save(testBook);

        assertEquals(testBook.getId(), savedBook.getId());
        assertEquals(testBook.getTitle(), savedBook.getTitle());
        assertEquals(testBook.getAuthor(), savedBook.getAuthor());
        assertEquals(testBook.getIsbn(), savedBook.getIsbn());
        assertEquals(testBook.getPrice(), savedBook.getPrice());
        verify(springDataBookRepository).save(any(BookEntity.class));
    }

    @Test
    void IDで本を検索できる() {
        when(springDataBookRepository.findById(1L)).thenReturn(Optional.of(testEntity));

        Optional<Book> found = jpaBookRepository.findById(1L);

        assertTrue(found.isPresent());
        assertEquals(testBook.getId(), found.get().getId());
        assertEquals(testBook.getTitle(), found.get().getTitle());
        verify(springDataBookRepository).findById(1L);
    }

    @Test
    void 存在しない本をIDで検索すると空のOptionalが返される() {
        when(springDataBookRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Book> found = jpaBookRepository.findById(99L);

        assertFalse(found.isPresent());
        verify(springDataBookRepository).findById(99L);
    }

    @Test
    void すべての本を取得できる() {
        BookEntity anotherEntity = BookEntity.fromDomain(
            new Book(2L, "リファクタリング", "Martin Fowler",
                new ISBN("978-4-274-22456-4"),
                new Price(new BigDecimal("4400")))
        );
        
        when(springDataBookRepository.findAll())
            .thenReturn(Arrays.asList(testEntity, anotherEntity));

        List<Book> books = jpaBookRepository.findAll();

        assertEquals(2, books.size());
        assertEquals(testBook.getId(), books.get(0).getId());
        assertEquals("リファクタリング", books.get(1).getTitle());
        verify(springDataBookRepository).findAll();
    }

    @Test
    void 著者名で本を検索できる() {
        when(springDataBookRepository.findByAuthor("Kent Beck"))
            .thenReturn(Arrays.asList(testEntity));

        List<Book> books = jpaBookRepository.findByAuthor("Kent Beck");

        assertEquals(1, books.size());
        assertEquals(testBook.getTitle(), books.get(0).getTitle());
        verify(springDataBookRepository).findByAuthor("Kent Beck");
    }

    @Test
    void 本を削除できる() {
        jpaBookRepository.delete(1L);
        verify(springDataBookRepository).deleteById(1L);
    }
} 