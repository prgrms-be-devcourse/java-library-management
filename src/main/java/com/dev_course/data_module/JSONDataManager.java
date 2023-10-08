package com.dev_course.data_module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class JSONDataManager<T> implements DataManager<T> {
    private final File file;
    private final ObjectMapper objectMapper;
    private final ObjectReader objectReader;

    private String path = "./src/main/resources/library_data.json";

    public JSONDataManager(Class<T> type) {
        file = new File(path);
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectReader = objectMapper.readerForListOf(type);
    }

    @Override
    public List<T> load() {
        if (!file.exists()) {
            return Collections.emptyList();
        }

        try {
            return objectReader.readValue(Files.readString(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException("파일을 불러오는 과정에서 오류가 발생했습니다.");
        }
    }

    @Override
    public void save(List<T> data) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            objectMapper.writeValue(file, data);
        } catch (IOException e) {
            throw new RuntimeException("파일을 생성할 수 없습니다. (%s)".formatted(path));
        }
    }
}
