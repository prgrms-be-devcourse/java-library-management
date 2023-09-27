package com.programmers.config;

import com.programmers.domain.repository.BookRepository;

public class AppConfig {
    private static AppConfig instance;
    private BookRepository bookRepository;

    private AppConfig() {}

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public void initializeRepository(BookRepository userPickedRepository) {
        this.bookRepository = userPickedRepository;
    }

    public BookRepository getBookRepository() {
        if(bookRepository == null) {
            throw new IllegalStateException("아직 초기화 되지 않음");
        }
        return bookRepository;
    }

}
