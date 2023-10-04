package com.programmers.config.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.domain.repository.BookRepository;
import com.programmers.infrastructure.repository.FileMemoryBookRepository;
import com.programmers.util.AppProperties;
import com.programmers.util.IdGenerator;
import com.programmers.util.Messages;

public class NormalModeFactory implements ModeAbstractFactory {

    private BookRepository bookRepository;

    // 테스트를 위해
    NormalModeFactory() {
    }

    private static class SingleInstanceHolder {
        private static final NormalModeFactory INSTANCE = new NormalModeFactory();
    }

    public static NormalModeFactory getInstance() {
        return SingleInstanceHolder.INSTANCE;
    }

    @Override
    public BookRepository createBookRepository(ObjectMapper objectMapper) {
        if (this.bookRepository == null) {
            this.bookRepository = createNewFileMemoryBookRepository(objectMapper);
        }

        return this.bookRepository;
    }

    @Override
    public IdGenerator createIdGenerator() {
        Long lastRowId = Long.valueOf(AppProperties.getProperty("file.lastRowNumber"));
        return new IdGenerator(lastRowId);
    }

    @Override
    public String getModeMessage() {
        return Messages.SELECT_MODE_NORMAL.getMessage();
    }

    protected FileMemoryBookRepository createNewFileMemoryBookRepository(ObjectMapper objectMapper) {
        return new FileMemoryBookRepository(objectMapper);
    }
}
