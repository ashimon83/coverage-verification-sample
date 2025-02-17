package com.example.demo.infrastructure.repository;

import com.example.demo.domain.model.Book;
import com.example.demo.domain.repository.BookRepository;
import com.example.demo.infrastructure.entity.BookEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaBookRepository implements BookRepository {
    private final SpringDataBookRepository repository;

    public JpaBookRepository(SpringDataBookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        BookEntity entity = BookEntity.fromDomain(book);
        BookEntity savedEntity = repository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return repository.findById(id)
            .map(BookEntity::toDomain);
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll().stream()
            .map(BookEntity::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return repository.findByAuthor(author).stream()
            .map(BookEntity::toDomain)
            .collect(Collectors.toList());
    }
} 