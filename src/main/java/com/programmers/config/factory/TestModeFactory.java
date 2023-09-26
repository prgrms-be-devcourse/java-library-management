package com.programmers.config.factory;

import com.programmers.domain.repository.BookRepository;
import com.programmers.infrastructure.repository.ListBookRepository;

public class TestModeFactory implements ModeAbstractFactory {
    private TestModeFactory() {}
    private static class SingleInstanceHolder {
        private static final TestModeFactory INSTANCE = new TestModeFactory();
    }
    public static TestModeFactory getInstance() {
        return TestModeFactory.SingleInstanceHolder.INSTANCE;
    }
    @Override
    public BookRepository createBookRepository() {
        return new ListBookRepository();
    }
}
