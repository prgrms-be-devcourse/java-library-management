package com.programmers.infrastructure.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.domain.entity.Book;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class FileMemoryBookRepository extends MemoryBookRepository {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String currentDir = System.getProperty("user.dir");
    private final String specifiedPath = "src/main/resources";
    private final File BookFile = new File(currentDir + File.separator + specifiedPath + File.separator +"books.json");

    public FileMemoryBookRepository() {
        loadFromFile();
    }

    public void loadFromFile() {
        if (BookFile.exists()) {
            try {
                ConcurrentHashMap<String, Book> loadedBooks = objectMapper.readValue(
                        BookFile, new TypeReference<ConcurrentHashMap<String, Book>>() {}
                );
                super.books.putAll(loadedBooks);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                BookFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveToFile() {
        try {
            objectMapper.writeValue(BookFile, super.books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
