package com.example.demo.domain.model;

import java.util.Objects;

public class ISBN {
    private final String value;

    public ISBN(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("不正なISBN形式です");
        }
        this.value = value;
    }

    private boolean isValid(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        // ISBN-13の簡易バリデーション（実際の業務ではより厳密なチェックが必要）
        return isbn.replaceAll("-", "").matches("^\\d{13}$");
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ISBN isbn = (ISBN) o;
        return Objects.equals(value, isbn.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
} 