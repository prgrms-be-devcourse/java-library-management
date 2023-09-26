package com.programmers.factory;

import com.programmers.repository.BookRepository;
import com.programmers.repository.FileBookRepository;
import com.programmers.repository.MemBookRepository;

import java.util.HashMap;
import java.util.Map;

public class BookRepositoryFactory {
    private static final Map<Integer, BookRepository> repositories = new HashMap<>();

    static {
        repositories.put(0, new FileBookRepository());
        repositories.put(1, new MemBookRepository());
    }

    public static BookRepository getBookRepository(int mode) {
        return repositories.get(mode);
    }
}
