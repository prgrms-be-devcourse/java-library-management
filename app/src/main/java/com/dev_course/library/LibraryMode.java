package com.dev_course.library;

import com.dev_course.book.Book;
import com.dev_course.data.DataManager;
import com.dev_course.data.EmptyDataManager;

public enum LibraryMode {
    TEST(new EmptyDataManager<>()),
    NORMAL(new EmptyDataManager<>());

    private final DataManager<Book> dataManager;

    LibraryMode(DataManager<Book> dataManager) {
        this.dataManager = dataManager;
    }

    DataManager<Book> getDataManager() {
        return this.dataManager;
    }
}
