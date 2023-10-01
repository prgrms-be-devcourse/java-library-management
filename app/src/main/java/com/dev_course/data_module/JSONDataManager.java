package com.dev_course.data_module;

import com.dev_course.book.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

public class JSONDataManager implements DataManager {
    private final String path = "./src/main/resources/library_data.json";
    private final File file = new File(path);
    private final ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
    private final TypeReference<List<Book>> valueType = new TypeReference<>() {
    };

    @Override
    public List<Book> load() {
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(Files.readString(Paths.get(path)), valueType);
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException("파일을 불러오는 과정에서 오류가 발생했습니다.");
        }

    }

    @Override
    public void save(List<Book> books) {
        if (!file.exists()) {
            createDirectory();
        }

        try {
            objectMapper.writeValue(file, books);
        } catch (IOException e) {
            throw new RuntimeException("파일을 생성할 수 없습니다. (%s)".formatted(path));
        }
    }

    private void createDirectory() {
        file.getParentFile().mkdirs();
    }
}
