package org.example.server.repository;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.server.entity.Book;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

public class FileStorage {
    public FileStorage() throws IOException {
    }

    ObjectMapper objectMapper = new ObjectMapper();
    LinkedHashMap<Integer, Book> data = objectMapper.readValue(Paths.get("book.json").toFile(), LinkedHashMap.class);
    JsonParser jsonParser;
}
