package com.programmers.library.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.programmers.library.domain.Book;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileRepository implements Repository {

    private List<Book> storage;

    private static File DB;
    private final ObjectMapper mapper;

    public FileRepository() {
        DB = new File(System.getProperty("user.dir") + "/books.json");
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            storage = mapper.readValue(DB, new TypeReference<List<Book>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveToFile() {
        try {
            mapper.writeValue(DB, mapper.writeValueAsString(storage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int generateId() {
        //TODO: 수정 필요
        return storage.size() + 1;
    }

    @Override
    public void save(Book book) {
        storage.add(book);
        saveToFile();
    }

    @Override
    public List<Book> findAll() {
        return storage.stream()
                .toList();
    }

    @Override
    public List<Book> findAllByName(String name) {
        return storage.stream()
                .filter(book -> book.isNameContains(name))
                .toList();
    }

    @Override
    public Optional<Book> findOneById(int id) {
        return storage.stream()
                .filter(book -> book.isIdEqualTo(id))
                .findFirst();
    }

    @Override
    public void update(int id, Book updatedBook) {
        storage = storage.stream()
                .map(book -> book.isIdEqualTo(id) ? updatedBook : book)
                .collect(Collectors.toList());
        saveToFile();
    }

    @Override
    public void delete(int id) {
        storage = storage.stream()
                .filter(book -> !book.isIdEqualTo(id))
                .collect(Collectors.toList());
        saveToFile();
    }

    @Override
    public void deleteAll() {
        storage.clear();
        saveToFile();
    }
}
