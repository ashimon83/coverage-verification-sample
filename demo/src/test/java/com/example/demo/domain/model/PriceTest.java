package com.example.demo.domain.model;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @Test
    void 正の価格でインスタンスが生成される() {
        Price price = new Price(new BigDecimal("1000"));
        assertEquals(new BigDecimal("1000"), price.getAmount());
    }

    @Test
    void ゼロの価格でインスタンスが生成される() {
        Price price = new Price(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, price.getAmount());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"-1", "-1000"})
    void 負の価格または_nullの場合に例外がスローされる(String invalidAmount) {
        BigDecimal amount = invalidAmount != null ? new BigDecimal(invalidAmount) : null;
        assertThrows(IllegalArgumentException.class, () -> new Price(amount));
    }

    @Test
    void 価格の加算が正しく行われる() {
        Price price1 = new Price(new BigDecimal("1000"));
        Price price2 = new Price(new BigDecimal("500"));
        
        Price sum = price1.add(price2);
        assertEquals(new BigDecimal("1500"), sum.getAmount());
    }

    @Test
    void 価格の減算が正しく行われる() {
        Price price1 = new Price(new BigDecimal("1000"));
        Price price2 = new Price(new BigDecimal("300"));
        
        Price difference = price1.subtract(price2);
        assertEquals(new BigDecimal("700"), difference.getAmount());
    }

    @Test
    void 同じ価格は等しいと判定される() {
        Price price1 = new Price(new BigDecimal("1000"));
        Price price2 = new Price(new BigDecimal("1000"));
        Price differentPrice = new Price(new BigDecimal("2000"));

        assertEquals(price1, price2);
        assertNotEquals(price1, differentPrice);
        assertEquals(price1.hashCode(), price2.hashCode());
    }

    @Test
    void nullとの比較で等しくないと判定される() {
        Price price = new Price(new BigDecimal("1000"));
        assertNotEquals(null, price);
    }

    @Test
    void 異なる型との比較で等しくないと判定される() {
        Price price = new Price(new BigDecimal("1000"));
        assertNotEquals(new BigDecimal("1000"), price);
    }
} 