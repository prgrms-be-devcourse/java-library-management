package com.programmers.library.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.programmers.library.domain.Book;
import com.programmers.library.dto.JsonBookDto;
import com.programmers.library.util.JsonMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileRepository implements Repository {

    private static int sequance;
    private List<Book> storage = new ArrayList<>();

    private final File file = new File(System.getProperty("user.dir") + "/books.json");
    private final JsonMapper jsonMapper = new JsonMapper();

    public FileRepository() {
        List<JsonBookDto> books = jsonMapper.readValue(file, new TypeReference<List<JsonBookDto>>() {
        });

        for (JsonBookDto book : books) {
            storage.add(new Book(
                    book.getId(),
                    book.getName(),
                    book.getAuthor(),
                    book.getPageCount(),
                    book.getStatus(),
                    book.getReturnedAt()
            ));
        }

        sequance = books.size();
    }

    private void saveToFile() {
        jsonMapper.writeValue(file, storage);
    }

    @Override
    public int generateId() {
        return ++sequance;
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
        storage.stream()
                .filter(book -> book.isIdEqualTo(id))
                .findFirst()
                .ifPresent(book -> book = updatedBook);
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
