package com.dev_course.library;

import com.dev_course.book.Book;
import com.dev_course.book.BookManager;
import com.dev_course.book.ListBookManager;
import com.dev_course.data_module.DataManager;
import com.dev_course.data_module.EmptyDataManager;
import com.dev_course.data_module.JSONDataManager;

import java.util.List;

public enum LibraryMode {
    TEST(new EmptyDataManager()),
    NORMAL(new JSONDataManager());

    private final DataManager dataManager;

    LibraryMode(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    DataManager getDataManager() {
        return this.dataManager;
    }

    BookManager getBookManager() {
        List<Book> data = dataManager.load();

        return switch (this) {
            case TEST, NORMAL -> new ListBookManager(data);
        };
    }
}
