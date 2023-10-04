package com.programmers.infrastructure.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.domain.entity.Book;
import com.programmers.exception.unchecked.FileIOException;
import com.programmers.exception.unchecked.SystemErrorException;
import com.programmers.util.AppProperties;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FileMemoryBookRepository extends MemoryBookRepository {

    private final ObjectMapper objectMapper;
    private static final String currentDir = System.getProperty("user.dir");
    private final String specifiedPath = AppProperties.getProperty("file.specifiedPath");
    private final File bookFile = new File(
        currentDir + File.separator + specifiedPath + File.separator + AppProperties.getProperty(
            "file.bookFilePath"));

    public FileMemoryBookRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        // 초기화 시키는 부분.
        loadFromFile();
    }

    public void loadFromFile() {
        if (bookFile.exists()) {
            try {
                if (bookFile.length() > 0) {
                    ConcurrentHashMap<Long, Book> loadedBooks = objectMapper.readValue(
                        bookFile, new TypeReference<ConcurrentHashMap<Long, Book>>() {
                        }
                    );
                    super.books.putAll(loadedBooks);
                }
            } catch (IOException e) {
                throw new SystemErrorException();
            }
        } else {
            try {
                bookFile.createNewFile();
            } catch (IOException e) {
                throw new SystemErrorException();
            }
        }
    }

    public void saveToFile() throws IOException {
        synchronized (this) {
            objectMapper.writeValue(bookFile, super.books);
        }
    }

    public Book save(Book book) {
        Book savedBook = super.save(book);
        try {
            saveToFile();
            AppProperties.setFileLastNumber(savedBook.getId());
        } catch (IOException e) {
            super.deleteById(savedBook.getId());
            throw new FileIOException();
        }
        return book;
    }

    public int deleteById(Long id) {
        Optional<Book> savedState = super.findById(id);
        int result = super.deleteById(id);
        try {
            if (result == 1) {
                saveToFile();
            }
        } catch (IOException e) {
            savedState.ifPresent(book -> super.books.put(id, book));
            throw new FileIOException();
        }
        return result;
    }

    public int update(Book book) {
        Optional<Book> savedState = super.findById(book.getId());
        var result = super.update(book);
        try {
            saveToFile();
        } catch (IOException e) {
            savedState.ifPresent(super::update);
            throw new FileIOException();
        }
        return result;
    }

}
