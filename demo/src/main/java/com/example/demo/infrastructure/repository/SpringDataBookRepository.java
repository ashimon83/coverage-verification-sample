package com.example.demo.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.infrastructure.entity.BookEntity;

@Repository
public interface SpringDataBookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByAuthor(String author);
} 