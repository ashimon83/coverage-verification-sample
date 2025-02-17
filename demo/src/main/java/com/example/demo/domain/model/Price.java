package com.example.demo.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    private final BigDecimal amount;

    public Price(BigDecimal amount) {
        if (!isValid(amount)) {
            throw new IllegalArgumentException("価格は0円以上である必要があります");
        }
        this.amount = amount.setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    private boolean isValid(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) >= 0;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Price add(Price other) {
        return new Price(this.amount.add(other.amount));
    }

    public Price subtract(Price other) {
        return new Price(this.amount.subtract(other.amount));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(amount, price.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
} 