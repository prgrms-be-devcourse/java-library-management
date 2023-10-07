package com.programmers.infrastructure.IO.provider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.FileProvider;
import com.programmers.exception.unchecked.SystemErrorException;
import com.programmers.util.AppProperties;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;

// 이름이 어색하다 해서 Provider로 변경
@RequiredArgsConstructor
public class JsonFileProvider implements FileProvider<Book> {

    private final ObjectMapper objectMapper;
    private final File repositoryFile;

    public JsonFileProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        String currentDir = System.getProperty("user.dir");
        String specifiedPath = AppProperties.getProperty("file.specifiedPath");
        this.repositoryFile = new File(currentDir + File.separator + specifiedPath + File.separator
            + AppProperties.getProperty("file.bookFilePath"));
    }

    @Override
    public ConcurrentHashMap<Long, Book> loadFromFile() {
        if (repositoryFile.exists()) {
            try {
                if (repositoryFile.length() > 0) {
                    return objectMapper.readValue(
                        repositoryFile, new TypeReference<ConcurrentHashMap<Long, Book>>() {
                        }
                    );
                }
            } catch (IOException e) {
                throw new SystemErrorException();
            }
        } else {
            try {
                repositoryFile.createNewFile();
            } catch (IOException e) {
                throw new SystemErrorException();
            }
        }
        return new ConcurrentHashMap<>();
    }

    @Override
    public void saveToFile(ConcurrentHashMap<Long, Book> items) throws IOException {
        synchronized (this) {
            objectMapper.writeValue(repositoryFile, items);
        }
    }
}
