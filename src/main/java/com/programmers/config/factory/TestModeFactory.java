package com.programmers.config.factory;

import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.BookRepository;
import com.programmers.domain.repository.FileProvider;
import com.programmers.infrastructure.repository.ListBookRepository;
import com.programmers.util.IdGenerator;
import com.programmers.util.Messages;

public class TestModeFactory implements ModeAbstractFactory {

    private TestModeFactory() {
    }

    private static class SingleInstanceHolder {

        private static final TestModeFactory INSTANCE = new TestModeFactory();
    }

    public static TestModeFactory getInstance() {
        return TestModeFactory.SingleInstanceHolder.INSTANCE;
    }

    @Override
    public BookRepository createBookRepository(FileProvider<Book> fileProvider) {
        return new ListBookRepository();
    }

    @Override
    public IdGenerator createIdGenerator() {
        return new IdGenerator();
    }

    @Override
    public String getModeMessage() {
        return Messages.SELECT_MODE_TEST.getMessage();
    }

    protected ListBookRepository createNewListBookRepository() {
        return new ListBookRepository();
    }
}
