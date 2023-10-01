package com.dev_course.data_module;

import com.dev_course.book.Book;

import java.util.ArrayList;
import java.util.List;

public class EmptyDataManager implements DataManager {
    @Override
    public List<Book> load() {
        return new ArrayList<>();
    }

    @Override
    public void save(List<Book> books) {

    }
}
