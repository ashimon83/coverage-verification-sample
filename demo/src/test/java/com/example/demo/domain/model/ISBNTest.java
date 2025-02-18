package com.example.demo.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ISBNTest {

    @Test
    void 正しいISBN13の場合にインスタンスが生成される() {
        String validIsbn = "978-4-8222-8803-5";
        ISBN isbn = new ISBN(validIsbn);
        assertEquals(validIsbn, isbn.getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
        " ",
        "invalid-isbn",
        "978-4-8222-8803", // 短すぎる
        "978-4-8222-8803-5-1", // 長すぎる
        "abc-4-8222-8803-5" // 数字以外を含む
    })
    void 不正なISBNの場合に例外がスローされる(String invalidIsbn) {
        assertThrows(IllegalArgumentException.class, () -> new ISBN(invalidIsbn));
    }

    @Test
    void 同じ値を持つISBNは等しいと判定される() {
        ISBN isbn1 = new ISBN("978-4-8222-8803-5");
        ISBN isbn2 = new ISBN("978-4-8222-8803-5");
        ISBN differentIsbn = new ISBN("978-4-8222-8804-2");

        assertEquals(isbn1, isbn2);
        assertNotEquals(isbn1, differentIsbn);
        assertEquals(isbn1.hashCode(), isbn2.hashCode());
    }

    @Test
    void nullとの比較で等しくないと判定される() {
        ISBN isbn = new ISBN("978-4-8222-8803-5");
        assertNotEquals(null, isbn);
    }

    @Test
    void 異なる型との比較で等しくないと判定される() {
        ISBN isbn = new ISBN("978-4-8222-8803-5");
        assertNotEquals("978-4-8222-8803-5", isbn);
    }
} 