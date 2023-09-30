package com.dev_course.library;

import com.dev_course.book.Book;
import com.dev_course.book.BookManager;
import com.dev_course.book.ListBookManager;
import com.dev_course.data.EmptyLibraryDataManager;
import com.dev_course.data.LibraryDataManager;

import java.util.List;

public enum LibraryMode {
    TEST(new EmptyLibraryDataManager()),
    NORMAL(new EmptyLibraryDataManager());

    private final LibraryDataManager dataManager;

    LibraryMode(LibraryDataManager dataManager) {
        this.dataManager = dataManager;
    }

    LibraryDataManager getDataManager() {
        return this.dataManager;
    }

    BookManager getBookManager() {
        List<Book> data = dataManager.load();
        int seed = dataManager.getSeed();

        return switch (this) {
            case TEST, NORMAL -> new ListBookManager(data, seed);
        };
    }
}
