package com.programmers.config.factory;

import com.programmers.domain.repository.BookRepository;
import com.programmers.infrastructure.repository.FileMemoryBookRepository;

public class NormalModeFactory implements ModeAbstractFactory {
    private NormalModeFactory() {}
    private static class SingleInstanceHolder {
        private static final NormalModeFactory INSTANCE = new NormalModeFactory();
    }
    public static NormalModeFactory getInstance() {
        return SingleInstanceHolder.INSTANCE;
    }
    @Override
    public BookRepository createBookRepository() {
        return new FileMemoryBookRepository();
    }
}
