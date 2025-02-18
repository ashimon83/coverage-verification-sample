package com.example.demo.presentation.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.application.service.BookService;
import com.example.demo.domain.model.Book;
import com.example.demo.domain.model.ISBN;
import com.example.demo.domain.model.Price;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book(
            1L,
            "テスト駆動開発",
            "Kent Beck",
            new ISBN("978-4-274-21788-7"),
            new Price(new BigDecimal("3300"))
        );
    }

    @Test
    void 本を作成できる() throws Exception {
        when(bookService.createBook(anyString(), anyString(), anyString(), any(BigDecimal.class)))
            .thenReturn(testBook);

        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("テスト駆動開発");
        request.setAuthor("Kent Beck");
        request.setIsbn("978-4-274-21788-7");
        request.setPrice(new BigDecimal("3300"));

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("テスト駆動開発"))
            .andExpect(jsonPath("$.author").value("Kent Beck"));

        verify(bookService).createBook(
            request.getTitle(),
            request.getAuthor(),
            request.getIsbn(),
            request.getPrice()
        );
    }

    @Test
    void IDで本を取得できる() throws Exception {
        when(bookService.getBook(1L)).thenReturn(Optional.of(testBook));

        mockMvc.perform(get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("テスト駆動開発"))
            .andExpect(jsonPath("$.author").value("Kent Beck"));

        verify(bookService).getBook(1L);
    }

    @Test
    void 存在しない本をIDで取得すると404が返される() throws Exception {
        when(bookService.getBook(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/99"))
            .andExpect(status().isNotFound());

        verify(bookService).getBook(99L);
    }

    @Test
    void すべての本を取得できる() throws Exception {
        Book anotherBook = new Book(
            2L,
            "リファクタリング",
            "Martin Fowler",
            new ISBN("978-4-274-22456-4"),
            new Price(new BigDecimal("4400"))
        );

        when(bookService.getAllBooks()).thenReturn(Arrays.asList(testBook, anotherBook));

        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].title").value("テスト駆動開発"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].title").value("リファクタリング"));

        verify(bookService).getAllBooks();
    }

    @Test
    void 著者名で本を検索できる() throws Exception {
        when(bookService.getBooksByAuthor("Kent Beck"))
            .thenReturn(Arrays.asList(testBook));

        mockMvc.perform(get("/api/books/author/Kent Beck"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].title").value("テスト駆動開発"))
            .andExpect(jsonPath("$[0].author").value("Kent Beck"));

        verify(bookService).getBooksByAuthor("Kent Beck");
    }

    @Test
    void 本を更新できる() throws Exception {
        Book updatedBook = new Book(
            1L,
            "新しいタイトル",
            "新しい著者",
            new ISBN("978-4-274-21788-7"),
            new Price(new BigDecimal("4400"))
        );

        when(bookService.updateBook(anyLong(), anyString(), anyString(), any(BigDecimal.class)))
            .thenReturn(updatedBook);

        UpdateBookRequest request = new UpdateBookRequest();
        request.setTitle("新しいタイトル");
        request.setAuthor("新しい著者");
        request.setPrice(new BigDecimal("4400"));

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("新しいタイトル"))
            .andExpect(jsonPath("$.author").value("新しい著者"));

        verify(bookService).updateBook(
            1L,
            request.getTitle(),
            request.getAuthor(),
            request.getPrice()
        );
    }

    @Test
    void 本を削除できる() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
            .andExpect(status().isNoContent());

        verify(bookService).deleteBook(1L);
    }
} 