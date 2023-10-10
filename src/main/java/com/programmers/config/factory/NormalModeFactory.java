package com.programmers.config.factory;

import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.BookRepository;
import com.programmers.domain.repository.FileProvider;
import com.programmers.infrastructure.repository.PersistentBookRepository;
import com.programmers.util.AppProperties;
import com.programmers.util.IdGenerator;
import com.programmers.util.Messages;

public class NormalModeFactory implements ModeAbstractFactory {

    private BookRepository bookRepository;

    //TODO: 테스트때문에 추가
    NormalModeFactory() {
    }

    private static class SingleInstanceHolder {
        private static final NormalModeFactory INSTANCE = new NormalModeFactory();
    }

    public static NormalModeFactory getInstance() {
        return SingleInstanceHolder.INSTANCE;
    }

    @Override
    public BookRepository createBookRepository(FileProvider<Book> fileProvider) {
        if (this.bookRepository == null) {
            this.bookRepository = createNewFileMemoryBookRepository(fileProvider);
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

    protected PersistentBookRepository createNewFileMemoryBookRepository(
        FileProvider<Book> fileRepository) {
        return new PersistentBookRepository(fileRepository);
    }
}
