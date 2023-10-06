package com.programmers.infrastructure.repository;

import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.BookRepository;
import com.programmers.domain.repository.FileProvider;
import com.programmers.exception.unchecked.FileIOException;
import com.programmers.util.AppProperties;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PersistentBookRepository implements BookRepository {
    private final FileProvider<Book> jsonFileRepository;
    private final ConcurrentHashMap<Long, Book> books;

    public PersistentBookRepository(FileProvider<Book> jsonFileRepository) {
        this.jsonFileRepository = jsonFileRepository;
        // 초기화
        books = jsonFileRepository.loadFromFile();
    }

    @Override
    public Book save(Book book) {
        books.put(book.getId(), book);
        try {
            jsonFileRepository.saveToFile(books);
            AppProperties.setFileLastNumber(book.getId());
        } catch (IOException e) {
            books.remove(book.getId());
            throw new FileIOException();
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public int deleteById(Long id) {
        Optional<Book> savedState = findById(id);
        Book result = books.remove(id);
        try {
            if (result != null) jsonFileRepository.saveToFile(books);
        } catch (IOException e) {
            savedState.ifPresent(book -> books.put(id, book));
            throw new FileIOException();
        }
        return result != null ? 1 : 0;
    }

    @Override
    public int update(Book book) {
        Optional<Book> savedState = findById(book.getId());
        Book result = books.replace(book.getId(), book);
        try {
            if (result != null) jsonFileRepository.saveToFile(books);
        } catch (IOException e) {
            savedState.ifPresent(savedBook -> books.replace(book.getId(), savedBook));
            throw new FileIOException();
        }
        return result != null ? 1 : 0;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return books.values().stream().filter(book -> book.getTitle().contains(title)).toList();
    }

}
