package com.programmers.domain.repository;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public interface FileProvider<T> {

    ConcurrentHashMap<Long, T> loadFromFile();

    void saveToFile(ConcurrentHashMap<Long, T> items) throws IOException;

}
