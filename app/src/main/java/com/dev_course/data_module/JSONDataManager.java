package com.dev_course.data_module;

import com.dev_course.book.Book;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.core.util.DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;

public class JSONDataManager implements DataManager {
    private final String path = "./src/main/resources/library_data.json";
    private final File file = new File(path);
    private final PrettyPrinter pp = new DefaultPrettyPrinter(DEFAULT_ROOT_VALUE_SEPARATOR);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private LibraryData data;

    @Override
    public void load() {
        if (!file.exists()) {
            data = new LibraryData(0, new ArrayList<>());
            return;
        }

        try {
            data = objectMapper.readValue(file, LibraryData.class);
        } catch (IOException e) {
            throw new RuntimeException("파일을 불러오는 과정에서 오류가 발생했습니다.");
        }

    }

    @Override
    public void save(int seed, List<Book> books) {
        if (!file.exists()) {
            createDirectory();
        }

        try {
            objectMapper.writer(pp)
                    .writeValue(file, new LibraryData(seed, books));
        } catch (IOException e) {
            throw new RuntimeException("파일을 생성할 수 없습니다. (%s)".formatted(path));
        }
    }

    @Override
    public int getSeed() {
        return data.seed();
    }

    @Override
    public List<Book> getBooks() {
        return data.books();
    }

    private void createDirectory() {
        file.getParentFile().mkdirs();
    }
}
