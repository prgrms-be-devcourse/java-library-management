package com.dev_course.library;

import com.dev_course.book.BookManager;
import com.dev_course.book.ListBookManager;
import com.dev_course.data_module.DataManager;
import com.dev_course.data_module.EmptyDataManager;
import com.dev_course.data_module.JSONDataManager;

public enum ModeFactory {
    TEST, NORMAL;

    public DataManager getDataManager() {
        return switch (this) {
            case TEST -> new EmptyDataManager();
            case NORMAL -> new JSONDataManager();
        };
    }

    public BookManager getBookManager() {
        return switch (this) {
            case TEST, NORMAL -> new ListBookManager();
        };
    }
}
