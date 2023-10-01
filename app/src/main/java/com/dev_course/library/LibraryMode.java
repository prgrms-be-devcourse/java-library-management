package com.dev_course.library;

import com.dev_course.book.BookManager;
import com.dev_course.book.LoadableBookManager;
import com.dev_course.book.ListBookManager;
import com.dev_course.data_module.DataManager;
import com.dev_course.data_module.EmptyDataManager;
import com.dev_course.data_module.JSONDataManager;

public enum LibraryMode {
    TEST, NORMAL;

    private final DataManager dataManager;
    private final LoadableBookManager bookManager;

    LibraryMode() {
        dataManager = switch (this) {
            case TEST -> new EmptyDataManager();
            case NORMAL -> new JSONDataManager();
        };

        bookManager = switch (this) {
            case TEST, NORMAL -> new ListBookManager();
        };
    }

    public void init() {
        bookManager.init(dataManager.load());
    }

    public void close() {
        dataManager.save(bookManager.getBookList());
    }

    public BookManager getBookManager() {
        return bookManager;
    }
}
